package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementCacheRepository.class.getName());

	private static QuantityMeasurementCacheRepository instance;
	private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

	private QuantityMeasurementCacheRepository() {
		logger.info("CacheRepository initialized");
	}

	public static QuantityMeasurementCacheRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementCacheRepository();
		}
		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		cache.add(entity);
		logger.info("Saved entity: " + entity.getOperation());
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		return new ArrayList<>(cache);
	}

	@Override
	public void deleteAll() {
		cache.clear();
		logger.info("Cache cleared");
	}
}