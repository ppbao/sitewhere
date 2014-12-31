/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.spi.device.request;

import java.util.List;
import java.util.Map;

import com.sitewhere.spi.common.IMetadataProvider;
import com.sitewhere.spi.device.batch.IBatchOperation;
import com.sitewhere.spi.device.batch.OperationType;

/**
 * Provides information needex to create an {@link IBatchOperation}.
 * 
 * @author Derek
 */
public interface IBatchOperationCreateRequest extends IMetadataProvider {

	/**
	 * Get the unique token.
	 * 
	 * @return
	 */
	public String getToken();

	/**
	 * Get operation to be performed.
	 * 
	 * @return
	 */
	public OperationType getOperationType();

	/**
	 * Get operation parameters.
	 * 
	 * @return
	 */
	public Map<String, String> getParameters();

	/**
	 * Get list of hardware ids for devices to be operated on.
	 * 
	 * @return
	 */
	public List<String> getHardwareIds();
}