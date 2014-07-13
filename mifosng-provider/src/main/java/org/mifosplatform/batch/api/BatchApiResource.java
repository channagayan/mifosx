package org.mifosplatform.batch.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.mifosplatform.batch.domain.BatchRequest;
import org.mifosplatform.batch.domain.BatchResponse;
import org.mifosplatform.batch.exception.ErrorHandler;
import org.mifosplatform.batch.exception.ErrorInfo;
import org.mifosplatform.batch.serialization.BatchRequestJsonHelper;
import org.mifosplatform.batch.service.BatchApiService;
import org.mifosplatform.infrastructure.core.serialization.ToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Provides a REST resource for Batch Requests. This class acts as a proxy to 
 * {@link org.mifosplatform.batch.service.BatchApiService} and de-serializes 
 * the incoming JSON string to a list of {@link org.mifosplatform.batch.domain
 * .BatchRequest} type. This list is forwarded to BatchApiService which finally
 * returns a list of {@link org.mifosplatform.batch.domain.BatchResponse} type
 * which is then serialized into JSON response by this Resource class.
 * 
 * @author Rishabh Shukla
 * 
 * @see org.mifosplatform.batch.service.BatchApiService
 * @see org.mifosplatform.batch.domain.BatchRequest
 * @see org.mifosplatform.batch.domain.BatchResponse
 */
@Path("/batches")
@Component
@Scope("singleton")
public class BatchApiResource {
	
	private final PlatformSecurityContext context;
	private final ToApiJsonSerializer<BatchResponse> toApiJsonSerializer; 
	private final BatchApiService service;
	private final BatchRequestJsonHelper batchRequestJsonHelper;	
	
	/**
	 * Constructs a 'BatchApiService' with context, toApiJsonSerializer, service
	 * and batchRequestJsonHelper. 
	 * 
	 * @param context
	 * @param toApiJsonSerializer
	 * @param service
	 * @param batchRequestJsonHelper
	 */
	@Autowired
	public BatchApiResource(final PlatformSecurityContext context, final ToApiJsonSerializer<BatchResponse>
			toApiJsonSerializer, final BatchApiService service, final BatchRequestJsonHelper batchRequestJsonHelper) {
		
		this.context = context;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.service = service;
		this.batchRequestJsonHelper = batchRequestJsonHelper;
	}
	
	/**
	 * Rest assured POST method to get {@link BatchRequest} and returns back the 
	 * consolidated {@link BatchResponse}
	 *
	 * @param jsonRequestString
	 * @return serialized JSON
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String handleBatchRequests(@DefaultValue("false") @QueryParam("enclosingTransaction")
				final boolean enclosingTransaction, final String jsonRequestString) {
		
		//Handles user authentication
		this.context.authenticatedUser();															
		
		//Converts request array into BatchRequest List		
		final List<BatchRequest> requestList = this.batchRequestJsonHelper.extractList(jsonRequestString);

		//Gets back the consolidated BatchResponse from BatchApiservice 
		List<BatchResponse> result = new ArrayList<>();

		//If the request is to be handled as a Transaction. All requests will be rolled back on error
		if(enclosingTransaction) {
			
			try {
				result = service.handleBatchRequestsWithEnclosingTransaction(requestList);
			}
			catch(RuntimeException e) {
				
				BatchResponse response = new BatchResponse();
				
				//Gets an object of type ErrorInfo, containing information about raised exception
				ErrorInfo ex = ErrorHandler.handler(e);
				
				response.setStatusCode(ex.getStatusCode());
				response.setBody(ex.getMessage());				
				result.add(response);
				
				return this.toApiJsonSerializer.serialize(result);
				
			}
			
		}		
		else {
			result = service.handleBatchRequestsWithoutEnclosingTransaction(requestList);
		}

		return this.toApiJsonSerializer.serialize(result);
		
	}
}
