/**
 * 
 */
package com.honeybuns.serverless.api.util;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * @author shardulsrivastava
 *
 */
public class ContextUtils {

	public static Context getContext() {
		return new MockContext();
	}

}

class MockContext implements Context {

	public String getAwsRequestId() {
		return null;
	}

	public String getLogGroupName() {
		return null;
	}

	public String getLogStreamName() {
		return null;
	}

	public String getFunctionName() {
		return null;
	}

	public String getFunctionVersion() {
		return null;
	}

	public String getInvokedFunctionArn() {
		return null;
	}

	public CognitoIdentity getIdentity() {
		return null;
	}

	public ClientContext getClientContext() {
		return null;
	}

	public int getRemainingTimeInMillis() {
		return 0;
	}

	public int getMemoryLimitInMB() {
		return 0;
	}

	public LambdaLogger getLogger() {
		return null;
	}

}
