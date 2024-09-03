package com.training.internal.operations;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.RouterCompletionCallback;
import org.mule.runtime.extension.api.runtime.route.Chain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.internal.routes.ElseRoute;
import com.training.internal.routes.IfRouter;

public class CustomOperations {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomOperations.class);
	
	@Alias("IfElse")
	public void ifElseRouter(@Alias("If") IfRouter ifRoute, 
							@Alias("Else") ElseRoute elseRoute, 
							RouterCompletionCallback callback) {
		Chain chain;
		if (ifRoute.isCondition()) {
			logger.info("If route is executed");
			chain = ifRoute.getChain();
		} else {
			logger.info("Else route is executed");
			chain = elseRoute.getChain();
		}
		Consumer<Result> onSuccess = result -> callback.success(result);
		BiConsumer<Throwable, Result> onError = (error, result) -> callback.error(error);
		chain.process(onSuccess, onError);
	}

}
