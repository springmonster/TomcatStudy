/* ========================================================================= *
 *                                                                           *
 *                 The Apache Software License,  Version 1.1                 *
 *                                                                           *
 *          Copyright (c) 1999-2001 The Apache Software Foundation.          *
 *                           All rights reserved.                            *
 *                                                                           *
 * ========================================================================= *
 *                                                                           *
 * Redistribution and use in source and binary forms,  with or without modi- *
 * fication, are permitted provided that the following conditions are met:   *
 *                                                                           *
 * 1. Redistributions of source code  must retain the above copyright notice *
 *    notice, this list of conditions and the following disclaimer.          *
 *                                                                           *
 * 2. Redistributions  in binary  form  must  reproduce the  above copyright *
 *    notice,  this list of conditions  and the following  disclaimer in the *
 *    documentation and/or other materials provided with the distribution.   *
 *                                                                           *
 * 3. The end-user documentation  included with the redistribution,  if any, *
 *    must include the following acknowlegement:                             *
 *                                                                           *
 *       "This product includes  software developed  by the Apache  Software *
 *        Foundation <http://www.apache.org/>."                              *
 *                                                                           *
 *    Alternately, this acknowlegement may appear in the software itself, if *
 *    and wherever such third-party acknowlegements normally appear.         *
 *                                                                           *
 * 4. The names  "The  Jakarta  Project",  "WebApp",  and  "Apache  Software *
 *    Foundation"  must not be used  to endorse or promote  products derived *
 *    from this  software without  prior  written  permission.  For  written *
 *    permission, please contact <apache@apache.org>.                        *
 *                                                                           *
 * 5. Products derived from this software may not be called "Apache" nor may *
 *    "Apache" appear in their names without prior written permission of the *
 *    Apache Software Foundation.                                            *
 *                                                                           *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES *
 * INCLUDING, BUT NOT LIMITED TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY *
 * AND FITNESS FOR  A PARTICULAR PURPOSE  ARE DISCLAIMED.  IN NO EVENT SHALL *
 * THE APACHE  SOFTWARE  FOUNDATION OR  ITS CONTRIBUTORS  BE LIABLE  FOR ANY *
 * DIRECT,  INDIRECT,   INCIDENTAL,  SPECIAL,  EXEMPLARY,  OR  CONSEQUENTIAL *
 * DAMAGES (INCLUDING,  BUT NOT LIMITED TO,  PROCUREMENT OF SUBSTITUTE GOODS *
 * OR SERVICES;  LOSS OF USE,  DATA,  OR PROFITS;  OR BUSINESS INTERRUPTION) *
 * HOWEVER CAUSED AND  ON ANY  THEORY  OF  LIABILITY,  WHETHER IN  CONTRACT, *
 * STRICT LIABILITY, OR TORT  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN *
 * ANY  WAY  OUT OF  THE  USE OF  THIS  SOFTWARE,  EVEN  IF  ADVISED  OF THE *
 * POSSIBILITY OF SUCH DAMAGE.                                               *
 *                                                                           *
 * ========================================================================= *
 *                                                                           *
 * This software  consists of voluntary  contributions made  by many indivi- *
 * duals on behalf of the  Apache Software Foundation.  For more information *
 * on the Apache Software Foundation, please see <http://www.apache.org/>.   *
 *                                                                           *
 * ========================================================================= */

/**
 * @author  Pier Fumagalli <mailto:pier@betaversion.org>
 * @version $Id: wa.h,v 1.16 2002/05/03 13:30:25 pier Exp $
 */
#ifndef _WA_H_
#define _WA_H_

/* C standard includes */
#include <stdio.h>
#include <stdarg.h>
#include <string.h>

#ifdef WIN32
#define vsnprintf _vsnprintf
#else
#include <unistd.h>
#endif /* ifdef WIN32 */

/* APR Library includes */
#include <apr_general.h>
#include <apr_pools.h>
#include <apr_strings.h>
#include <apr_tables.h>
#include <apr_tables.h>
#include <apr_time.h>
#include <apr_network_io.h>
#include <apr_file_info.h>
#if APR_HAS_THREADS
#include <apr_thread_mutex.h>
#include <apr_atomic.h>
#endif

/* WebApp Library type definitions. */
typedef enum {
    wa_false,
    wa_true,
} wa_boolean;

typedef struct wa_chain wa_chain;

typedef struct wa_application wa_application;
typedef struct wa_virtualhost wa_virtualhost;
typedef struct wa_connection wa_connection;

typedef struct wa_request wa_request;
typedef struct wa_ssldata wa_ssldata;
typedef struct wa_hostdata wa_hostdata;
typedef struct wa_handler wa_handler;

typedef struct wa_provider wa_provider;

/* All declared providers */
extern wa_provider wa_provider_info;
extern wa_provider wa_provider_warp;
/*extern wa_provider wa_provider_jni;*/

/* WebApp Library includes */
#include <wa_main.h>
#include <wa_config.h>
#include <wa_request.h>
#include <wa_version.h>

/* Debugging mark */
#define WA_MARK __FILE__,__LINE__

#endif /* ifndef _WA_H_ */
