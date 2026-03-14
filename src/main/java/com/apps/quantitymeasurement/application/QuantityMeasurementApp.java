package com.apps.quantitymeasurement.application;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.util.ApplicationConfig;
import com.apps.quantitymeasurement.util.ConnectionPool;

import java.util.logging.Logger;

public class QuantityMeasurementApp {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementApp.class.getName());

	private static QuantityMeasurementApp instance;
	private final QuantityMeasurementController controller;
	private final IQuantityMeasurementRepository repository;

	private QuantityMeasurementApp() {
		ApplicationConfig config = ApplicationConfig.getInstance();
		this.repository = createRepository(config);
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
		this.controller = new QuantityMeasurementController(service);
		logger.info("App initialized with " + repository.getClass().getSimpleName());
	}

	private IQuantityMeasurementRepository createRepository(ApplicationConfig config) {
		String repoType = config.getRepositoryType();
		if ("database".equalsIgnoreCase(repoType)) {
			logger.info("Using DatabaseRepository");
			ConnectionPool pool = new ConnectionPool(config.getDbUrl(), config.getDbUsername(), config.getDbPassword(),
					config.getMaxConnections());
			return new QuantityMeasurementDatabaseRepository(pool);
		}
		logger.info("Using CacheRepository");
		return QuantityMeasurementCacheRepository.getInstance();
	}

	public static QuantityMeasurementApp getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}

	public QuantityMeasurementController getController() {
		return controller;
	}

	public void deleteAllMeasurements() {
		repository.deleteAll();
		logger.info("All measurements deleted");
	}

	public void closeResources() {
		repository.releaseResources();
		logger.info("Resources released");
	}

	public void printSummary() {
		logger.info("Total measurements: " + repository.getTotalCount());
		logger.info("Pool stats: " + repository.getPoolStatistics());
	}

	public static void main(String[] args) {
		QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();
		QuantityMeasurementController controller = app.getController();

		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		controller.performConversion(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET), "INCHES");

		controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES), "FEET");

		controller.performSubtraction(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES), "FEET");

		controller.performDivision(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));

		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
				new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM));

		controller.performConversion(new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM), "GRAM");

		controller.performComparison(new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
				new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));

		controller.performConversion(new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS), "FAHRENHEIT");

		controller.performAddition(new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
				new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS), "CELSIUS");

		app.printSummary();
		app.deleteAllMeasurements();
		app.closeResources();
	}
}