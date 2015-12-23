/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.web.configuration.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sitewhere.web.configuration.model.ElementRole.Serializer;

/**
 * Used to indicate role of an element.
 * 
 * @author Derek
 */
@JsonSerialize(using = Serializer.class)
public enum ElementRole {

	/** Top level element. */
	Globals_Global("Global", true, true, true),

	/** Globals element. */
	Globals(null, false, true, false, new ElementRole[] { Globals_Global }),

	/** Data management container. Datastore configuration. */
	DataManagement_Datastore("Datastore", false, false, false),

	/** Data management container. Cache provider configuration. */
	DataManagement_CacheProvider("Cache Provider", true, false, false),

	/** Data management container. Device model initializer configuration. */
	DataManagement_DeviceModelInitializer("Device Model Initializer", true, false, false),

	/** Data management container. Asset model initializer configuration. */
	DataManagement_AssetModelInitializer("Asset Model Initializer", true, false, false),

	/** Data management container. Schedule model initializer configuration. */
	DataManagement_ScheduleModelInitializer("Schedule Model Initializer", true, false, false),

	/** Data management. */
	DataManagement(null, false, true, false, new ElementRole[] {
			DataManagement_Datastore,
			DataManagement_CacheProvider,
			DataManagement_DeviceModelInitializer,
			DataManagement_AssetModelInitializer,
			DataManagement_ScheduleModelInitializer }),

	/** Event source. Binary event decoder. */
	EventSource_BinaryEventDecoder("Binary Event Decoder", true, false, false),

	/** Event sources container. Event source. */
	EventSources_EventSource("Event Source", true, true, true,
			new ElementRole[] { EventSource_BinaryEventDecoder }),

	/** Device communication container. Event sources configuration. */
	DeviceCommunication_EventSources(null, false, false, false,
			new ElementRole[] { EventSources_EventSource }),

	/** Inbound processing strategy container. Blocking queue strategy. */
	InboundProcessingStrategy_Strategy("Strategy", false, false, false),

	/** Device communication container. Inbound processing strategy. */
	DeviceCommunication_InboundProcessingStrategy(null, false, false, false,
			new ElementRole[] { InboundProcessingStrategy_Strategy }),

	/** Registration container. Registration manager. */
	Registration_RegistrationManager("Registration Manager", false, false, false),

	/** Device communication container. Registration. */
	DeviceCommunication_Registration(null, false, false, false,
			new ElementRole[] { Registration_RegistrationManager }),

	/** Batch operations container. Batch operation manager. */
	BatchOperations_BatchOperationManager("Batch Operation Manager", false, false, false),

	/** Device communication container. Batch operations. */
	DeviceCommunication_BatchOperations(null, false, false, false,
			new ElementRole[] { BatchOperations_BatchOperationManager }),

	/** Command routing container. Command router implementation. */
	CommandRouting_CommandRouter("Command Router", false, false, false),

	/** Specification mapping router. Mapping. */
	CommandRouting_SpecificationMappingRouter_Mapping("Mapping", true, true, true),

	/** Device communication container. Command routing configuration. */
	DeviceCommunication_CommandRouting(null, false, false, false,
			new ElementRole[] { CommandRouting_CommandRouter }),

	/** Command destination. Binary command encoder. */
	CommandDestinations_BinaryCommandEncoder("Binary Command Encoder", false, false, false),

	/** Command destination. Paramter extractor. */
	CommandDestinations_ParameterExtractor("Parameter Extractor", false, false, false),

	/** Command destinations. Command destination. */
	CommandDestinations_CommandDestination("Command Destination", true, true, true, new ElementRole[] {
			CommandDestinations_BinaryCommandEncoder,
			CommandDestinations_ParameterExtractor }),

	/** Device communication container. Command destinations configuration. */
	DeviceCommunication_CommandDestinations(null, false, false, false,
			new ElementRole[] { CommandDestinations_CommandDestination }),

	/** Device communication element. */
	DeviceCommunication(null, false, true, false, new ElementRole[] {
			DeviceCommunication_EventSources,
			DeviceCommunication_InboundProcessingStrategy,
			DeviceCommunication_Registration,
			DeviceCommunication_BatchOperations,
			DeviceCommunication_CommandRouting,
			DeviceCommunication_CommandDestinations }),

	/** Inbound processing chain. Event processor. */
	InboundProcessingChain_EventProcessor("Event Processor", true, true, true),

	/** Inbound processing chain element. */
	InboundProcessingChain(null, false, true, false,
			new ElementRole[] { InboundProcessingChain_EventProcessor }),

	/** Outbound processing chain element. */
	OutboundProcessingChain(null, false, true, false, new ElementRole[] {}),

	/** Asset Management element. */
	AssetManagment(null, false, true, false, new ElementRole[] {}),

	/** Top level element. */
	Root(null, false, true, false, new ElementRole[] {
			Globals,
			DataManagement,
			DeviceCommunication,
			InboundProcessingChain,
			OutboundProcessingChain,
			AssetManagment });

	/** Role name */
	private String name;

	/** Indicates if role is optional */
	private boolean optional;

	/** Indicates if multiple elements in role are allowed */
	private boolean multiple;

	/** Indicates if elements in role can be reordered */
	private boolean reorderable;

	/** Child roles in the order they should appear */
	private ElementRole[] children;

	private ElementRole(String name, boolean optional, boolean multiple, boolean reorderable) {
		this(name, optional, multiple, reorderable, new ElementRole[0]);
	}

	private ElementRole(String name, boolean optional, boolean multiple, boolean reorderable,
			ElementRole[] children) {
		this.name = name;
		this.optional = optional;
		this.multiple = multiple;
		this.reorderable = reorderable;
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public boolean isReorderable() {
		return reorderable;
	}

	public void setReorderable(boolean reorderable) {
		this.reorderable = reorderable;
	}

	public ElementRole[] getChildren() {
		return children;
	}

	public void setChildren(ElementRole[] children) {
		this.children = children;
	}

	public static class Serializer extends JsonSerializer<ElementRole> {

		public void serialize(ElementRole value, JsonGenerator generator, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			generator.writeStartObject();
			generator.writeFieldName("name");
			generator.writeString(value.getName());
			generator.writeFieldName("optional");
			generator.writeBoolean(value.isOptional());
			generator.writeFieldName("multiple");
			generator.writeBoolean(value.isMultiple());
			generator.writeFieldName("reorderable");
			generator.writeBoolean(value.isReorderable());

			if ((value.getChildren() != null) && (value.getChildren().length > 0)) {
				generator.writeArrayFieldStart("children");
				for (ElementRole child : value.getChildren()) {
					generator.writeString(child.name());
				}
				generator.writeEndArray();
			}

			generator.writeEndObject();
		}
	}
}