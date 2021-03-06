/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2009-2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.jpa.event.internal.core;

import java.io.Serializable;

import org.hibernate.event.internal.DefaultMergeEventListener;
import org.hibernate.event.spi.EventSource;
import org.hibernate.jpa.event.internal.jpa.CallbackRegistryConsumer;
import org.hibernate.jpa.event.spi.jpa.CallbackRegistry;

/**
 * Overrides the LifeCycle OnSave call to call the PrePersist operation
 *
 * @author Emmanuel Bernard
 */
public class JpaMergeEventListener extends DefaultMergeEventListener implements CallbackRegistryConsumer {
	private CallbackRegistry callbackRegistry;

	public void injectCallbackRegistry(CallbackRegistry callbackRegistry) {
		this.callbackRegistry = callbackRegistry;
	}

	public JpaMergeEventListener() {
		super();
	}

	public JpaMergeEventListener(CallbackRegistry callbackRegistry) {
		super();
		this.callbackRegistry = callbackRegistry;
	}

	@Override
	protected Serializable saveWithRequestedId(
			Object entity,
			Serializable requestedId,
			String entityName,
			Object anything,
			EventSource source) {
		callbackRegistry.preCreate( entity );
		return super.saveWithRequestedId( entity, requestedId, entityName, anything, source );
	}

	@Override
	protected Serializable saveWithGeneratedId(
			Object entity,
			String entityName,
			Object anything,
			EventSource source,
			boolean requiresImmediateIdAccess) {
		callbackRegistry.preCreate( entity );
		return super.saveWithGeneratedId( entity, entityName, anything, source, requiresImmediateIdAccess );
	}
}
