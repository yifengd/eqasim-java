package org.eqasim.san_francisco.mode_choice.parameters;

import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;

public class SanFranciscoModeParameters extends ModeParameters {

	public class SanFranciscoWalkParameters {
		public double alpha_walk_city = 0.0;
	}

	public class SanFranciscoBikeParameters {
		public double alpha_bike_city = 0.0;
	}

	public class SanFranciscoCarParameters {
		public double alpha_car_city = 0.0;
	}

	public class SanFranciscoPTParameters {
		public double alpha_pt_city = 0.0;
		public double travelTime_min;
	}

	public class SanFranciscoIncomeElasticity {
		public double lambda_income = 0.0;
	}

	public class SanFranciscoAvgHHLIncome {
		public double avg_hhl_income = 0.0;
	}

	public final SanFranciscoWalkParameters sfWalk = new SanFranciscoWalkParameters();
	public final SanFranciscoBikeParameters sfBike = new SanFranciscoBikeParameters();
	public final SanFranciscoCarParameters sfCar = new SanFranciscoCarParameters();
	public final SanFranciscoPTParameters sfPT = new SanFranciscoPTParameters();
	public final SanFranciscoIncomeElasticity sfIncomeElasticity = new SanFranciscoIncomeElasticity();
	public final SanFranciscoAvgHHLIncome sfAvgHHLIncome = new SanFranciscoAvgHHLIncome();

	public static SanFranciscoModeParameters buildDefault() {
		SanFranciscoModeParameters parameters = new SanFranciscoModeParameters();

		// Cost
		parameters.betaCost_u_MU = -0.2354;
		parameters.lambdaCostEuclideanDistance = 0.0;
		parameters.referenceEuclideanDistance_km = 40.0;
		parameters.sfIncomeElasticity.lambda_income = -0.1827;
		parameters.sfAvgHHLIncome.avg_hhl_income = 124108;
		// Car
		parameters.car.alpha_u = 0.0;
		parameters.sfCar.alpha_car_city = 0.0;
		parameters.car.betaTravelTime_u_min = -0.0727;
		parameters.car.constantAccessEgressWalkTime_min = 0.0;
		parameters.car.constantParkingSearchPenalty_min = 0.0;

		// PT
		parameters.pt.alpha_u = -3.3026;
		parameters.sfPT.travelTime_min = -0.0100;
		parameters.sfPT.alpha_pt_city = 1.678;

		// Bike
		parameters.bike.alpha_u = -1.0;
		parameters.bike.betaTravelTime_u_min = -0.1080;
		parameters.sfBike.alpha_bike_city = 0.7149;

		parameters.bike.betaAgeOver18_u_a = 0.0;

		// Walk
		parameters.walk.alpha_u = 0.5295;
		parameters.walk.betaTravelTime_u_min = -0.1080;
		parameters.sfWalk.alpha_walk_city = 0.7149;


		return parameters;
	}
}