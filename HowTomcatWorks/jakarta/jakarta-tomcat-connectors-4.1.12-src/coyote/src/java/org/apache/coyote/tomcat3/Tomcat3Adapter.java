/*
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * [Additional notices, if required by prior licensing conditions]
 *
 */ 


package org.apache.coyote.tomcat3;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import org.apache.tomcat.core.*;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.util.buf.*;
import org.apache.tomcat.util.http.*;
import org.apache.tomcat.util.net.*;
import org.apache.tomcat.util.net.ServerSocketFactory;
import org.apache.tomcat.util.log.*;
import org.apache.coyote.Adapter;
import org.apache.coyote.Processor;

/** Adapter between Coyote and Tomcat.
 *
 *  This class handles the task of passing of an individual request to
 *  Tomcat to handle.  Also some of the connection-specific methods are
 *  delegated to here.
 *  @Author Bill Barker
 */
public class Tomcat3Adapter implements Adapter {
    ContextManager cm;
    
    Tomcat3Adapter(ContextManager ctxman) {
	cm   = ctxman;
    }

    static int containerRequestNOTE=1; // XXX Implement a NoteManager, namespaces.
    
    /** Pass off an individual request to Tomcat.
     */
    public void service(org.apache.coyote.Request request, 
			org.apache.coyote.Response response) 
	    throws Exception
    {
        Tomcat3Request reqA;
        Tomcat3Response resA;

        reqA=(Tomcat3Request)request.getNote( containerRequestNOTE );
        if( reqA==null ) {
            reqA=new Tomcat3Request();
            resA=new Tomcat3Response();
            cm.initRequest( reqA, resA );

            reqA.setCoyoteRequest(request);
            resA.setCoyoteResponse(response);
            request.setNote( containerRequestNOTE, reqA );
        } else {
            resA=(Tomcat3Response)reqA.getResponse();
        }
        
	    
	cm.service( reqA, resA );

        reqA.recycle();
        resA.recycle();
    }
}
