package com.training.internal.routes;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.route.Route;

public class IfRouter extends Route {

	@Parameter
	@Expression(ExpressionSupport.REQUIRED)
	@Summary("Please provide a conditional expression which results in true/false")
	private boolean condition;

	public boolean isCondition() {
		return condition;
	}
}

