package org.eqasim.projects.dynamic_av.mode_choice.utilities.estimators;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.utilities.estimators.EstimatorUtils;
import org.eqasim.projects.dynamic_av.mode_choice.DAModeParameters;
import org.eqasim.projects.dynamic_av.mode_choice.utilities.predictors.DABikePredictor;
import org.eqasim.projects.dynamic_av.mode_choice.utilities.predictors.DAPersonPredictor;
import org.eqasim.projects.dynamic_av.mode_choice.utilities.predictors.DATripPredictor;
import org.eqasim.projects.dynamic_av.mode_choice.utilities.variables.DABikeVariables;
import org.eqasim.projects.dynamic_av.mode_choice.utilities.variables.DAPersonVariables;
import org.eqasim.projects.dynamic_av.mode_choice.utilities.variables.DATripVariables;
import org.eqasim.switzerland.mode_choice.utilities.estimators.SwissBikeUtilityEstimator;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import com.google.inject.Inject;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;

public class DABikeUtilityEstimator extends SwissBikeUtilityEstimator {
	private final DAModeParameters parameters;
	private final DABikePredictor predictor;
	private final DAPersonPredictor personPredictor;
	private final DATripPredictor tripPredictor;

	@Inject
	public DABikeUtilityEstimator(DAModeParameters parameters, DABikePredictor predictor,
			DAPersonPredictor personPredictor, DATripPredictor tripPredictor) {
		super(parameters, personPredictor.delegate, predictor.delegate);

		this.parameters = parameters;
		this.predictor = predictor;
		this.personPredictor = personPredictor;
		this.tripPredictor = tripPredictor;
	}

	protected double estimateTravelTimeUtility(DABikeVariables variables) {
		return super.estimateTravelTimeUtility(variables) //
				* EstimatorUtils.interaction(variables.euclideanDistance_km, parameters.referenceEuclideanDistance_km,
						parameters.lambdaTravelTimeEuclideanDistance);
	}

	protected double estimateAgeUtility(DAPersonVariables variables) {
		return variables.age_a >= 60 ? parameters.daBike.betaAgeOver60 : 0.0;
	}

	protected double estimateWorkUtility(DATripVariables variables) {
		return variables.purpose.equals("work") ? parameters.daBike.betaWork : 0.0;
	}

	@Override
	public double estimateUtility(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		DABikeVariables variables = predictor.predictVariables(person, trip, elements);
		DAPersonVariables personVariables = personPredictor.predictVariables(person, trip, elements);
		DATripVariables tripVariables = tripPredictor.predictVariables(person, trip, elements);

		double utility = 0.0;

		utility += estimateConstantUtility();
		utility += estimateTravelTimeUtility(variables);
		utility += estimateAgeUtility(personVariables);
		utility += estimateWorkUtility(tripVariables);

		if (variables.travelTime_min > 60.0) {
			utility -= 100.0;
		}

		return utility;
	}
}