/**
 *
 */
package com.onepoint.poc.webapi.graphql.oql;

import java.lang.annotation.Annotation;
import java.util.Iterator;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.TypeLiteral;

/**
 * @author s.leduby
 */
public abstract class AbstractBeanInstance<T> implements Instance<T> {

	private T instance;

	public AbstractBeanInstance(final T instance) {
		this.instance = instance;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	public T get() {
		return instance;
	}

	@Override
	public Instance<T> select(final Annotation... qualifiers) {
		return null;
	}

	@Override
	public <U extends T> Instance<U> select(final Class<U> subtype, final Annotation... qualifiers) {
		return null;
	}

	@Override
	public <U extends T> Instance<U> select(final TypeLiteral<U> subtype, final Annotation... qualifiers) {
		return null;
	}

	@Override
	public boolean isUnsatisfied() {
		return false;
	}

	@Override
	public boolean isAmbiguous() {
		return false;
	}

	@Override
	public void destroy(final T instance) {
	}

}
