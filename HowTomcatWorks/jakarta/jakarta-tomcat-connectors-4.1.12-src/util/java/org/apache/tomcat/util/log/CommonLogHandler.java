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
 */ 
package org.apache.tomcat.util.log;

import org.apache.tomcat.util.log.*;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.StringWriter;

import java.util.*;

import org.apache.commons.logging.*;

/**
 *  Log using common-logging.
 *
 * @author Costin Manolache
 */
public  class CommonLogHandler extends LogHandler {

    private Hashtable loggers=new Hashtable();
    
    /**
     * Prints log message and stack trace.
     * This method should be overriden by real logger implementations
     *
     * @param	prefix		optional prefix. 
     * @param	message		the message to log. 
     * @param	t		the exception that was thrown.
     * @param	verbosityLevel	what type of message is this?
     * 				(WARNING/DEBUG/INFO etc)
     */
    public void log(String prefix, String msg, Throwable t,
		    int verbosityLevel)
    {
        if( prefix==null ) prefix="tomcat";
        org.apache.commons.logging.Log l=(org.apache.commons.logging.Log)loggers.get( prefix );
        if( l==null ) {
            l=LogFactory.getLog( prefix );
            loggers.put( prefix, l );
        }
        
	if( verbosityLevel > this.level ) return;

        if( t==null ) {
            switch( verbosityLevel ) {
            case Log.FATAL:
                l.fatal( msg );
                break;
            case Log.ERROR:
                l.error( msg );
                break;
            case Log.WARNING:
                l.warn( msg );
                break;
            case Log.INFORMATION:
                l.info( msg );
                break;
            case Log.DEBUG:
                l.debug( msg );
                break;
            }
        } else {
            switch( verbosityLevel ) {
            case Log.FATAL:
                l.fatal( msg, t );
                break;
            case Log.ERROR:
                l.error( msg, t );
                break;
            case Log.WARNING:
                l.warn( msg, t );
                break;
            case Log.INFORMATION:
                l.info( msg, t );
                break;
            case Log.DEBUG:
                l.debug( msg, t );
                break;
            }
        }
    }

    /**
     * Flush the log. 
     */
    public void flush() {
	// Nothing - commons logging doesn't have the notion
    }

    /**
     * Close the log. 
     */
    public synchronized void close() {
	// Nothing - commons logging doesn't have the notion
    }
    
}
