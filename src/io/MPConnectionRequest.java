package io;

/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores
 * CA 94065 USA or visit www.oracle.com if you need additional information or
 * have any questions.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import main.Constants;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.io.impl.IOImplementation;
import com.sun.lwuit.io.util.BufferedInputStream;
import com.sun.lwuit.io.util.BufferedOutputStream;
import com.sun.lwuit.io.util.IOProgressListener;
import com.sun.lwuit.io.util.Util;
import com.sun.lwuit.util.EventDispatcher;

/**
 * This class represents a connection object in the form of a request response
 * typically common for HTTP/HTTPS connections. Elements of this type are placed
 * in a priority queue based
 * 
 * @author Shai Almog
 */
public class MPConnectionRequest implements IOProgressListener
{
	/**
	 * A critical priority request will "push" through the queue to the highest
	 * point regardless of anything else and ignoring anything that is not in
	 * itself of critical priority. A critical priority will stop any none
	 * critical connection in progress
	 */
	public static final byte	PRIORITY_CRITICAL	= (byte) 100;
	/**
	 * A high priority request is the second highest level, it will act exactly
	 * like a critical priority with one difference. It doesn't block another
	 * incoming high priority request. E.g. if a high priority request
	 */
	public static final byte	PRIORITY_HIGH		= (byte) 80;
	/**
	 * Normal priority executes as usual on the queue
	 */
	public static final byte	PRIORITY_NORMAL		= (byte) 50;
	/**
	 * Low priority requests are mostly background tasks that should still be
	 * accomplished though
	 */
	public static final byte	PRIORITY_LOW		= (byte) 30;
	/**
	 * Redundant elements can be discarded from the queue when paused
	 */
	public static final byte	PRIORITY_REDUNDANT	= (byte) 0;
	protected EventDispatcher	actionListeners;

	final static public class HttpMethod
	{
		final public static int	POST	= 0;
		final public static int	PUT		= 1;
		final public static int	DELETE	= 2;
		final public static int	GET		= 3;
		final public static int	HEAD	= 4;
		final public static int	RESTORE	= 5;
	}
	final public static class ContentType
	{
		final public static int	FileItem			= 0;
		final public static int	ContainerItem		= 1;
		final public static int	MemberItem			= 2;
		final public static int	ShareItem			= 3;
		final public static int	CommentItem			= 4;
		final public static int	NotificationItem	= 5;
		final public static int	TrashItem			= 6;
		final public static int	Account				= 7;
		final public static int	Cloud				= 8;
		final public static int	CDMI				= 9;
		final public static int	FileList			= 10;
		final public static int	TrashList			= 11;
		final public static int	MemberList			= 12;
		final public static int	CommentList			= 13;
		final public static int	ShareList			= 14;
		final public static int	NotificationList	= 15;
		final public static int	Reference			= 16;
		final public static int	Blank				= 17;
		final public static int	Json				= 18;
		final public static int	Binary				= 19;
		final public static int	Multipart			= 20;
	}

	//------------------------------------------------------------
	final public static String GetHeaderForType(final int type)
	{
		String value = null;
		switch (type)
		{
			case ContentType.Account:
				value = "application/vnd.csp.account-info+json";
				break;
			case ContentType.Cloud:
				value = "application/vnd.csp.cloud3+json";
				break;
			case ContentType.CommentItem:
				value = "application/vnd.csp.comment+json";
				break;
			case ContentType.ContainerItem:
				value = "application/vnd.csp.container-info+json";
				break;
			case ContentType.FileItem:
				value = "application/vnd.csp.file-info2+json";
				break;
			case ContentType.MemberItem:
				value = "application/vnd.csp.member+json";
				break;
			case ContentType.ShareItem:
				value = "application/vnd.csp.space+json";
				break;
			case ContentType.TrashItem:
				value = "application/vnd.csp.deleted-object+json";
				break;
			case ContentType.TrashList:
				value = "application/vnd.csp.deleted-object-list+json";
				break;
			case ContentType.FileList:
				value = "application/vnd.csp.file-list+json";
				break;
			case ContentType.MemberList:
				value = "application/vnd.csp.member-list+json";
				break;
			case ContentType.CommentList:
				value = "application/vnd.csp.comment-list+json";
				break;
			case ContentType.ShareList:
				value = "application/vnd.csp.spaces+json";
				break;
			case ContentType.Blank:
				value = "*/*";
				break;
			case ContentType.Json:
				value = "application/json";
				break;
			case ContentType.NotificationItem:
				value = "application/vnd.csp.notification+json";
				break;
			case ContentType.NotificationList:
				value = "application/vnd.csp.notifications+json";
				break;
			case ContentType.CDMI:
				value = "application/vnd.csp.mezeo-member+json";
				break;
			case ContentType.Reference:
				value = "application/vnd.csp.reference+json";
				break;
			case ContentType.Binary:
				value = "binary/octet-stream";
				break;
			case ContentType.Multipart:
				value = "multipart/form-data";
				break;
			default:
				break;
		}
		return value;
	}

	final public static String GetMethodForHttp(final int method)
	{
		String value = null;
		switch (method)
		{
			case HttpMethod.DELETE:
				value = "DELETE";
				break;
			case HttpMethod.GET:
				value = "GET";
				break;
			case HttpMethod.POST:
				value = "POST";
				break;
			case HttpMethod.PUT:
				value = "PUT";
				break;
			case HttpMethod.HEAD:
				value = "HEAD";
				break;
			case HttpMethod.RESTORE:
				value = "RESTORE";
				break;
			default:
				break;
		}
		return value;
	}

	/**
	 * @return the defaultFollowRedirects
	 */
	public static boolean isDefaultFollowRedirects()
	{
		return defaultFollowRedirects;
	}

	/**
	 * @param aDefaultFollowRedirects
	 *            the defaultFollowRedirects to set
	 */
	public static void setDefaultFollowRedirects(boolean aDefaultFollowRedirects)
	{
		defaultFollowRedirects = aDefaultFollowRedirects;
	}

	protected byte				priority				= PRIORITY_NORMAL;
	protected long				timeSinceLastUpdate;
	protected Hashtable			requestArguments;
	protected boolean			post					= true;
	protected String			contentType				= "application/x-www-form-urlencoded; charset=UTF-8";
	protected static String		defaultUserAgent		= null;
	protected String			userAgent				= getDefaultUserAgent();
	protected String			url;
	protected boolean			writeRequest;
	protected boolean			readRequest				= true;
	protected boolean			paused;
	protected boolean			killed					= false;
	protected static boolean	defaultFollowRedirects	= true;
	protected boolean			followRedirects			= defaultFollowRedirects;
	protected int				timeout					= 300000;
	protected InputStream		input;
	protected OutputStream		output;
	protected int				progress				= MPNetworkEvent.PROGRESS_TYPE_OUTPUT;
	protected int				contentLength			= -1;
	protected boolean			duplicateSupported;
	protected EventDispatcher	responseCodeListeners;
	protected Hashtable			userHeaders;
	protected Dialog			showOnInit;
	protected Dialog			disposeOnCompletion;
	protected int				silentRetryCount		= 0;
	// /////////////////////////////////////////////////
	// /////////////////////////////////////////////////
	protected String			body					= null;
	protected boolean			isMultipart				= false;
	protected String			boundary				= null;
	protected boolean			showProgress			= false;
	protected Object			connection				= null;
	public int					actualResponseCode		= 0;

	public MPConnectionRequest()
	{
		super();
	}

	public MPConnectionRequest(final String _uri, final int _method, final int _contentType, final String _body, final int _depth, final boolean _showProgress, final Object _extraObject, final boolean _wait, final byte _priority, final ActionListener _tellMe)
	{
		loadData(_uri, _method, _contentType, _body, _depth, _showProgress, _extraObject, _wait, _priority, _tellMe);
	}

	public void loadData(final String _uri, final int _method, final int _contentType, final String _body, final int _depth, final boolean _showProgress, final Object _extraObject, final boolean _wait, final byte _priority, final ActionListener _tellMe)
	{
		setUrl(_uri);
		//
		switch (_method)
		{
			case HttpMethod.POST:
				setPost(true);
				break;
			case HttpMethod.GET:
				setPost(false);
				break;
			case HttpMethod.DELETE:
			case HttpMethod.PUT:
			case HttpMethod.HEAD:
				setPost(true);
				addRequestHeader("X-HTTP-Method-Override", GetMethodForHttp(_method));
				break;
			default:
				break;
		}
		//
		if (_contentType == ContentType.Reference)
		{
			addRequestHeader("Content-Location", (String) _extraObject);
			setContentType(GetHeaderForType(_contentType));
		}
		else if (_contentType == ContentType.Multipart)
		{
			setMultipart(true);
			//			ksi = (KlinkStorageItem) _extraObject;
			addRequestHeader("Transfer-Encoding", "chunked");
			setContentType("multipart/form-data; boundary=" + getBoundary());
		}
		else
		{
			setContentType(GetHeaderForType(_contentType));
		}
		//
		body = _body;
		//
		//addRequestHeader(KeyStrings.XCloudDepth, KlinkConstants.GetDepthForHttp(_depth));
		//
		setShowProgress(_showProgress);
		//
		setPriority(_priority);
		//
		setDuplicateSupported(false);
		//
		//addRequestHeader("Authorization", "GDL gdl_token=" /*+ ApplicationData.getAccessToken()*/ + ",gdl_id=" + ApplicationData.getUserID());
		//addRequestHeader("Accept", GetHeaderForType(ContentType.Blank));
		//addRequestHeader("X-Client-Specification", "3");
		//addRequestHeader("X-Client-Id", ApplicationData.getUniqueDeviceID());
		if (_tellMe != null)
		{
			addResponseCodeListener(_tellMe);
			addResponseListener(_tellMe);
		}
		if (_wait)
		{
			MPNetworkManager.getInstance().addToQueueAndWait(this);
		}
		else
		{
			MPNetworkManager.getInstance().addToQueue(this);
		}
	}

	protected void buildRequestBodyNew(final OutputStream os) throws IOException
	{
		if (isMultipart)
		{
			/*			final String boundaryMessage = getBoundaryMessage();
						final String endBoundary = "\r\n--" + boundary + "--\r\n";
						//
						InputStream ins = null;
						FileConnection fconn = null;
						try
						{
							// final DataOutputStream dos = new DataOutputStream(os);
							byte[] ba = boundaryMessage.getBytes("UTF-8");
							os.write(ba, 0, ba.length);
							os.flush();
							//
							fconn = (FileConnection) Connector.open(ksi.itemuri, Connector.READ);
							ins = fconn.openInputStream();
							int bfLength = 8192;
							if (os instanceof BufferedOutputStream)
							{
								bfLength = BufferedOutputStream.getDefaultBufferSize();
							}
							final byte[] bfr = new byte[bfLength];
							int lenRead = -1;
							do
							{
								lenRead = ins.read(bfr, 0, bfLength);
								if (lenRead <= 0)
								{
									break;
								}
								os.write(bfr, 0, lenRead);
								os.flush();
								if (shouldStop())
								{
									return;
								}
							} while (true);
							//
							ba = endBoundary.getBytes("UTF-8");
							os.write(ba, 0, ba.length);
						}
						catch (final OutOfMemoryError e)
						{
							e.printStackTrace();
						}
						catch (final Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							try
							{
								if (ins != null)
								{
									ins.close();
								}
								if (fconn != null)
								{
									fconn.close();
								}
							}
							catch (final Exception e2)
							{
								e2.printStackTrace();
							}
						}
						*/
		}
		else
		{
			if (body != null)
			{
				os.write(body.getBytes("UTF-8"));
			}
		}
	}

	// ///////////////////////
	public boolean isShowProgress()
	{
		return showProgress;
	}

	public void setShowProgress(boolean showProgress)
	{
		this.showProgress = showProgress;
	}

	public boolean isMultipart()
	{
		return isMultipart;
	}

	public void setMultipart(boolean isMultipart)
	{
		this.isMultipart = isMultipart;
	}

	public String getBoundary()
	{
		if (boundary == null)
		{
			boundary = generateBoundaryString();
		}
		return boundary;
	}

	String getBoundaryMessage()
	{
		final StringBuffer res = new StringBuffer("--").append(boundary).append("\r\n");
		//		res.append("Content-Disposition: form-data; name=\"").append("uploadedfile").append("\"; filename=\"").append(ksi.name).append("\"\r\n").append("Content-Type: ").append(ksi.mime_type).append("\r\n\r\n");
		return res.toString();
	}

	public Hashtable getUserHeaders()
	{
		return userHeaders;
	}

	public String generateBoundaryString()
	{
		final Random rand = new Random();
		String randomstr = Constants.emptyString;
		for (int i = 0; i < 4; i++)
		{
			randomstr = randomstr + Long.toString(rand.nextLong(), 36);
		}
		final String bdry = "----" + randomstr;
		return bdry;
	}

	public void setBody(final String bdy)
	{
		body = bdy;
	}

	// //////////////////////////////////////
	// //////////////////////////
	/**
	 * Adds the given header to the request that will be sent
	 * 
	 * @param key
	 *            the header key
	 * @param value
	 *            the header value
	 */
	public void addRequestHeader(String key, String value)
	{
		if (userHeaders == null)
		{
			userHeaders = new Hashtable();
		}
		userHeaders.put(key, value);
	}

	/**
	 * Adds the given header to the request that will be sent unless the header
	 * is already set to something else
	 * 
	 * @param key
	 *            the header key
	 * @param value
	 *            the header value
	 */
	void addRequestHeaderDontRepleace(String key, String value)
	{
		if (userHeaders == null)
		{
			userHeaders = new Hashtable();
		}
		if (!userHeaders.containsKey(key))
		{
			userHeaders.put(key, value);
		}
	}

	protected void prepare()
	{
		timeSinceLastUpdate = System.currentTimeMillis();
	}

	/**
	 * Invoked to initialize HTTP headers, cookies etc.
	 * 
	 * @param connection
	 *            the connection object
	 */
	protected void initConnection(Object connection)
	{
		timeSinceLastUpdate = System.currentTimeMillis();
		final IOImplementation impl = IOImplementation.getInstance();
		impl.setPostRequest(connection, isPost());
		if (getUserAgent() != null)
		{
			impl.setHeader(connection, "User-Agent", getUserAgent());
		}
		if (getContentType() != null)
		{
			impl.setHeader(connection, "Content-Type", getContentType());
		}
		if (userHeaders != null)
		{
			final Enumeration e = userHeaders.keys();
			while (e.hasMoreElements())
			{
				final String k = (String) e.nextElement();
				final String value = (String) userHeaders.get(k);
				impl.setHeader(connection, k, value);
			}
		}
	}

	/**
	 * Performs the actual network request on behalf of the network manager
	 */
	void performOperation() throws IOException
	{
		if (shouldStop())
		{
			return;
		}
		final IOImplementation impl = IOImplementation.getInstance();
		// Object connection = null;
		connection = null;
		input = null;
		output = null;
		try
		{
			final String actualUrl = createRequestURL();
			connection = impl.connect(actualUrl, isReadRequest(), isPost() || isWriteRequest());
			if (shouldStop())
			{
				return;
			}
			initConnection(connection);
			/*
			 * final Vector v = impl.getCookiesForURL(actualUrl); if (v != null)
			 * { final int c = v.size(); if (c > 0) { final StringBuffer
			 * cookieStr = new StringBuffer(); final Cookie first = (Cookie)
			 * v.elementAt(0); cookieStr.append(first.getName());
			 * cookieStr.append("="); cookieStr.append(first.getValue()); for
			 * (int iter = 1; iter < c; iter++) { final Cookie current =
			 * (Cookie) v.elementAt(iter); cookieStr.append(";");
			 * cookieStr.append(current.getName()); cookieStr.append("=");
			 * cookieStr.append(current.getValue()); }
			 * impl.setHeader(connection, "cookie", cookieStr.toString()); }
			 * 
			 * }
			 */
			if (isWriteRequest())
			{
				output = impl.openOutputStream(connection);
				if (shouldStop())
				{
					return;
				}
				// if (isShowProgress())
				{
					if (MPNetworkManager.getInstance().hasProgressListeners() && (output instanceof BufferedOutputStream))
					{
						System.out.println("setProgressListener");
						((BufferedOutputStream) output).setProgressListener(this);
					}
				}
				buildRequestBody(output);
				if (shouldStop())
				{
					return;
				}
				if (output instanceof BufferedOutputStream)
				{
					((BufferedOutputStream) output).flushBuffer();
					if (shouldStop())
					{
						return;
					}
				}
			}
			timeSinceLastUpdate = System.currentTimeMillis();
			final int responseCode = impl.getResponseCode(connection);
			/*
			 * final String[] cookies = impl.getHeaderFields("Set-Cookie",
			 * connection); if ((cookies != null) && (cookies.length > 0)) {
			 * final Vector cook = new Vector(); for (int iter = 0; iter <
			 * cookies.length; iter++) { final Cookie coo =
			 * parseCookieHeader(cookies[iter]); if (coo != null) {
			 * cook.addElement(coo); } } final Cookie[] arr = new
			 * Cookie[cook.size()]; for (int i = 0; i < arr.length; i++) {
			 * arr[i] = (Cookie) cook.elementAt(i); } impl.addCookie(arr); }
			 */
			actualResponseCode = responseCode;
			if ((responseCode < 200) || (responseCode >= 300))
			{
				// redirect to new location
				if (followRedirects && ((responseCode == 301) || (responseCode == 302) || (responseCode == 303)))
				{
					// if ((getUrl().indexOf("/thumbnail?s=") < 0) &&
					// ((responseCode == 301) || (responseCode == 302)))
					{
						final String uri = impl.getHeaderField("location", connection);
						if (!(uri.startsWith("http://") || uri.startsWith("https://")))
						{
							// relative URI's in the location header are illegal
							// but
							// some sites mistakenly use them
							url = Util.relativeToAbsolute(url, uri);
						}
						else
						{
							url = uri;
						}
						if (((responseCode == 302) || (responseCode == 303)))
						{
							if (post && shouldConvertPostToGetOnRedirect())
							{
								post = false;
								setWriteRequest(false);
							}
						}
						impl.cleanup(output);
						impl.cleanup(connection);
						connection = null;
						output = null;
						if (!onRedirect(url))
						{
							retry();
						}
						return;
					}
				}
				handleErrorResponseCode(responseCode, impl.getResponseMessage(connection));
				return;
			}
			readHeaders(connection);
			contentLength = impl.getContentLength(connection);
			timeSinceLastUpdate = System.currentTimeMillis();
			progress = MPNetworkEvent.PROGRESS_TYPE_INPUT;
			if (isReadRequest())
			{
				input = impl.openInputStream(connection);
				if (shouldStop())
				{
					return;
				}
				if (input instanceof BufferedInputStream)
				{
					if (MPNetworkManager.getInstance().hasProgressListeners())
					{
						((BufferedInputStream) input).setProgressListener(this);
					}
					((BufferedInputStream) input).setYield(getYield());
				}
				readResponse(input);
				if (shouldAutoCloseResponse())
				{
					input.close();
				}
				input = null;
			}
		}
		catch (final IOException ioe)
		{
			System.out.println(ioe.getMessage());
			handleErrorResponseCode(9999, ioe.getMessage());
		}
		finally
		{
			// always cleanup connections/streams even in case of an exception
			impl.cleanup(output);
			impl.cleanup(input);
			impl.cleanup(connection);
			timeSinceLastUpdate = -1;
			input = null;
			output = null;
			connection = null;
		}
	}

	/**
	 * This mimics the behavior of browsers that convert post operations to get
	 * operations when redirecting a request.
	 * 
	 * @return defaults to true, this case be modified by subclasses
	 */
	protected boolean shouldConvertPostToGetOnRedirect()
	{
		return true;
	}

	/**
	 * Allows reading the headers from the connection by calling the getHeader()
	 * method.
	 * 
	 * @param connection
	 *            used when invoking getHeader
	 * @throws java.io.IOException
	 *             thrown on failure
	 */
	protected void readHeaders(Object connection) throws IOException
	{
	}

	/**
	 * Returns the HTTP header field for the given connection, this method is
	 * only guaranteed to work when invoked from the readHeaders method.
	 * 
	 * @param connection
	 *            the connection to the network
	 * @param header
	 *            the name of the header
	 * @return the value of the header
	 * @throws java.io.IOException
	 *             thrown on failure
	 */
	protected String getHeader(Object connection, String header) throws IOException
	{
		return IOImplementation.getInstance().getHeaderField(header, connection);
	}

	/**
	 * Returns the amount of time to yield for other processes, this is an
	 * implicit method that automatically generates values for lower priority
	 * connections
	 * 
	 * @return yield duration or -1 for no yield
	 */
	protected int getYield()
	{
		if (priority > PRIORITY_NORMAL)
		{
			return -1;
		}
		if (priority == PRIORITY_NORMAL)
		{
			return 20;
		}
		return 40;
	}

	/**
	 * Indicates whether the response stream should be closed automatically by
	 * the framework (defaults to true), this might cause an issue if the stream
	 * needs to be passed to a separate thread for reading.
	 * 
	 * @return true to close the response stream automatically.
	 */
	protected boolean shouldAutoCloseResponse()
	{
		return true;
	}

	/**
	 * Parses a raw cookie header and returns a cookie object to send back at
	 * the server
	 * 
	 * @param h
	 *            raw cookie header
	 * @return the cookie object
	 */
	/*
	 * private Cookie parseCookieHeader(String h) { final Cookie c = new
	 * Cookie(); final int edge = h.indexOf(';'); final int equals =
	 * h.indexOf('='); if (equals < 0) { return null; } c.setName(h.substring(0,
	 * equals)); if (edge < 0) { c.setValue(h.substring(equals + 1));
	 * c.setDomain(IOImplementation.getInstance().getURLDomain(url)); return c;
	 * } else { c.setValue(h.substring(equals + 1, edge)); } int index =
	 * h.indexOf("domain="); if (index != -1) { String domain =
	 * h.substring(index + 7); index = domain.indexOf(';'); if (index != -1) {
	 * domain = domain.substring(0, index); } if (url.indexOf(domain) < 0) {
	 * //if (!hc.getHost().endsWith(domain)) {
	 * System.out.println("Warning: Cookie tried to set to another domain");
	 * c.setDomain(IOImplementation.getInstance().getURLDomain(url)); } else {
	 * c.setDomain(domain); } } else {
	 * c.setDomain(IOImplementation.getInstance().getURLDomain(url)); } return
	 * c; }
	 */
	/**
	 * Handles IOException thrown when performing a network operation
	 * 
	 * @param err
	 *            the exception thrown
	 */
	protected void handleIOException(IOException err)
	{
		handleException(err);
	}

	/**
	 * Handles an exception thrown when performing a network operation
	 * 
	 * @param err
	 *            the exception thrown
	 */
	protected void handleRuntimeException(RuntimeException err)
	{
		handleException(err);
	}

	/**
	 * Handles an exception thrown when performing a network operation, the
	 * default implementation shows a retry dialog.
	 * 
	 * @param err
	 *            the exception thrown
	 */
	protected void handleException(Exception err)
	{
		if (killed)
		{
			return;
		}
		err.printStackTrace();
		if (silentRetryCount > 0)
		{
			silentRetryCount--;
			retry();
			return;
		}
		if (responseCodeListeners != null)
		{
			final MPNetworkEvent n = new MPNetworkEvent(this, 600, err.getMessage());
			responseCodeListeners.fireActionEvent(n);
		}
		// if (Display.isInitialized() && Dialog.show("Exception",
		// err.toString() + ": " + err.getMessage(), "Retry", "Cancel"))
		// {
		// retry();
		// }
	}

	/**
	 * Handles a server response code that is not 200 and not a redirect (unless
	 * redirect handling is disabled)
	 * 
	 * @param code
	 *            the response code from the server
	 * @param message
	 *            the response message from the server
	 */
	protected void handleErrorResponseCode(int code, String message)
	{
		if (responseCodeListeners != null)
		{
			final MPNetworkEvent n = new MPNetworkEvent(this, code, message);
			responseCodeListeners.fireActionEvent(n);
		}
	}

	/**
	 * Retry the current operation in case of an exception
	 */
	public void retry()
	{
		MPNetworkManager.getInstance().addToQueue(this, true);
	}

	/**
	 * This is a callback method that been called when there is a redirect.
	 * 
	 * @param url
	 *            the url to be redirected
	 * @return true if the implementation would like to handle this by itself
	 */
	public boolean onRedirect(String url)
	{
		return false;
	}

	/**
	 * Callback for the server response with the input stream from the server.
	 * This method is invoked on the network thread
	 * 
	 * @param input
	 *            the input stream containing the response
	 * @throws IOException
	 *             when a read input occurs
	 */
	protected void readResponse(InputStream input) throws IOException
	{
		if (hasResponseListeners())
		{
			final byte[] data = Util.readInputStream(input);
			fireResponseListener(new MPNetworkEvent(this, data));
		}
	}

	/**
	 * Creates the request URL mostly for a get request
	 * 
	 * @return the string of a request
	 */
	protected String createRequestURL()
	{
		if (!post && (requestArguments != null))
		{
			final StringBuffer b = new StringBuffer(url);
			final Enumeration e = requestArguments.keys();
			if (e.hasMoreElements())
			{
				b.append("?");
			}
			while (e.hasMoreElements())
			{
				final String key = (String) e.nextElement();
				final String value = (String) requestArguments.get(key);
				b.append(key);
				b.append("=");
				b.append(value);
				if (e.hasMoreElements())
				{
					b.append("&");
				}
			}
			return b.toString();
		}
		return url;
	}

	/**
	 * Invoked when send body is true, by default sends the request arguments
	 * based on "POST" conventions
	 * 
	 * @param os
	 *            output stream of the body
	 */
	protected void buildRequestBody(OutputStream os) throws IOException
	{
		if (post && (requestArguments != null))
		{
			final StringBuffer val = new StringBuffer();
			final Enumeration e = requestArguments.keys();
			while (e.hasMoreElements())
			{
				final String key = (String) e.nextElement();
				final String value = (String) requestArguments.get(key);
				val.append(key);
				val.append("=");
				val.append(value);
				if (e.hasMoreElements())
				{
					val.append("&");
				}
			}
			if (IOImplementation.getInstance().shouldWriteUTFAsGetBytes())
			{
				os.write(val.toString().getBytes("UTF-8"));
			}
			else
			{
				final OutputStreamWriter w = new OutputStreamWriter(os, "UTF-8");
				w.write(val.toString());
			}
		}
		buildRequestBodyNew(os);
	}

	/**
	 * Kills this request if possible
	 */
	public void kill()
	{
		killed = true;
	}

	/**
	 * Returns true if the request is paused or killed, developers should call
	 * this method periodically to test whether they should quit the current IO
	 * operation immediately
	 * 
	 * @return true if the request is paused or killed
	 */
	protected boolean shouldStop()
	{
		return isPaused() || isKilled();
	}

	/**
	 * Return true from this method if this connection can be paused and resumed
	 * later on. A pausable network operation receives a "pause" invocation and
	 * is expected to stop network operations as soon as possible. It will later
	 * on receive a resume() call and optionally start downloading again.
	 * 
	 * @return false by default.
	 */
	protected boolean isPausable()
	{
		return false;
	}

	/**
	 * Invoked to pause this operation, this method will only be invoked if
	 * isPausable() returns true (its false by default). After this method is
	 * invoked current network operations should be stopped as soon as possible
	 * for this class.
	 * 
	 * @return This method can return false to indicate that there is no need to
	 *         resume this method since the operation has already been completed
	 *         or made redundant
	 */
	public boolean pause()
	{
		paused = true;
		return true;
	}

	/**
	 * Called when a previously paused operation now has the networking time to
	 * resume. Assuming this method returns true, the network request will be
	 * resent to the server and the operation can resume.
	 * 
	 * @return This method can return false to indicate that there is no need to
	 *         resume this method since the operation has already been completed
	 *         or made redundant
	 */
	public boolean resume()
	{
		paused = false;
		return true;
	}

	/**
	 * Returns true for a post operation and false for a get operation
	 * 
	 * @return the post
	 */
	public boolean isPost()
	{
		return post;
	}

	/**
	 * Returns true for a post operation and false for a get operation
	 * 
	 * @throws IllegalStateException
	 *             if invoked after an addArgument call
	 */
	public void setPost(boolean post)
	{
		if ((this.post != post) && (requestArguments != null) && (requestArguments.size() > 0))
		{
			throw new IllegalStateException("Request method (post/get) can't be modified one arguments have been assigned to the request");
		}
		this.post = post;
		if (this.post)
		{
			setWriteRequest(true);
		}
	}

	/**
	 * Add an argument to the request response
	 * 
	 * @param key
	 *            the key of the argument
	 * @param value
	 *            the value for the argument
	 */
	private void addArg(String key, Object value)
	{
		if (requestArguments == null)
		{
			requestArguments = new Hashtable();
		}
		if ((value == null) || (key == null))
		{
			return;
		}
		requestArguments.put(key, value);
	}

	/**
	 * Add an argument to the request response
	 * 
	 * @param key
	 *            the key of the argument
	 * @param value
	 *            the value for the argument
	 * @deprecated use the version that accepts a string instead
	 */
	public void addArgument(String key, byte[] value)
	{
		key = key.intern();
		if (post)
		{
			addArg(key, Util.encodeBody(value));
		}
		else
		{
			addArg(key, Util.encodeUrl(value));
		}
	}

	/**
	 * Removes the given argument from the request
	 * 
	 * @param key
	 *            the key of the argument no longer used
	 */
	public void removeArgument(String key)
	{
		if (requestArguments != null)
		{
			requestArguments.remove(key);
		}
	}

	/**
	 * Add an argument to the request response without encoding it, this is
	 * useful for arguments which are already encoded
	 * 
	 * @param key
	 *            the key of the argument
	 * @param value
	 *            the value for the argument
	 */
	public void addArgumentNoEncoding(String key, String value)
	{
		addArg(key, value);
	}

	/**
	 * Add an argument to the request response
	 * 
	 * @param key
	 *            the key of the argument
	 * @param value
	 *            the value for the argument
	 */
	public void addArgument(String key, String value)
	{
		if (post)
		{
			addArg(key, Util.encodeBody(value));
		}
		else
		{
			addArg(key, Util.encodeUrl(value));
		}
	}

	/**
	 * @return the contentType
	 */
	public String getContentType()
	{
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	/**
	 * @return the writeRequest
	 */
	public boolean isWriteRequest()
	{
		return writeRequest;
	}

	/**
	 * @param writeRequest
	 *            the writeRequest to set
	 */
	public void setWriteRequest(boolean writeRequest)
	{
		this.writeRequest = writeRequest;
	}

	/**
	 * @return the readRequest
	 */
	public boolean isReadRequest()
	{
		return readRequest;
	}

	/**
	 * @param readRequest
	 *            the readRequest to set
	 */
	public void setReadRequest(boolean readRequest)
	{
		this.readRequest = readRequest;
	}

	/**
	 * @return the paused
	 */
	protected boolean isPaused()
	{
		return paused;
	}

	/**
	 * @param paused
	 *            the paused to set
	 */
	protected void setPaused(boolean paused)
	{
		this.paused = paused;
	}

	/**
	 * @return the killed
	 */
	protected boolean isKilled()
	{
		return killed;
	}

	/**
	 * @param killed
	 *            the killed to set
	 */
	protected void setKilled(boolean killed)
	{
		this.killed = killed;
	}

	/**
	 * The priority of this connection based on the constants in this class
	 * 
	 * @return the priority
	 */
	public byte getPriority()
	{
		return priority;
	}

	/**
	 * The priority of this connection based on the constants in this class
	 * 
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(byte priority)
	{
		this.priority = priority;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent()
	{
		return userAgent;
	}

	/**
	 * @param userAgent
	 *            the userAgent to set
	 */
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	/**
	 * @return the defaultUserAgent
	 */
	public static String getDefaultUserAgent()
	{
		return defaultUserAgent;
	}

	/**
	 * @param aDefaultUserAgent
	 *            the defaultUserAgent to set
	 */
	public static void setDefaultUserAgent(String aDefaultUserAgent)
	{
		defaultUserAgent = aDefaultUserAgent;
	}

	/**
	 * @return the followRedirects
	 */
	public boolean isFollowRedirects()
	{
		return followRedirects;
	}

	/**
	 * @param followRedirects
	 *            the followRedirects to set
	 */
	public void setFollowRedirects(boolean followRedirects)
	{
		this.followRedirects = followRedirects;
	}

	/**
	 * Indicates the timeout for this connection request
	 * 
	 * @return the timeout
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * Indicates the timeout for this connection request
	 * 
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	/**
	 * This method prevents a manual timeout from occuring when invoked at a
	 * frequency faster than the timeout.
	 */
	void updateActivity()
	{
		timeSinceLastUpdate = System.currentTimeMillis();
	}

	/**
	 * Returns the time since the last activity update
	 */
	public int getTimeSinceLastActivity()
	{
		if ((input != null) && (input instanceof BufferedInputStream))
		{
			final long t = ((BufferedInputStream) input).getLastActivityTime();
			if (t > timeSinceLastUpdate)
			{
				timeSinceLastUpdate = t;
			}
		}
		if ((output != null) && (output instanceof BufferedOutputStream))
		{
			final long t = ((BufferedOutputStream) output).getLastActivityTime();
			if (t > timeSinceLastUpdate)
			{
				timeSinceLastUpdate = t;
			}
		}
		return (int) (System.currentTimeMillis() - timeSinceLastUpdate);
	}

	/**
	 * Returns the content legth header value
	 * 
	 * @return the content length
	 */
	public int getContentLength()
	{
		return contentLength;
	}

	/**
	 * @inheritDoc
	 */
	public void ioStreamUpdate(Object source, int bytes)
	{
		MPNetworkManager.getInstance().fireProgressEvent(this, progress, contentLength, bytes);
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		url = url.intern();
		this.url = url;
	}

	/**
	 * Adds a listener that would be notified on the LWUIT thread of a response
	 * from the server. This event is specific to the connection request type
	 * and its firing will change based on how the connection request is
	 * read/processed
	 * 
	 * @param a
	 *            listener
	 */
	public void addResponseListener(ActionListener a)
	{
		if (actionListeners == null)
		{
			actionListeners = new EventDispatcher();
			actionListeners.setBlocking(false);
		}
		actionListeners.addListener(a);
	}

	/**
	 * Removes the given listener
	 * 
	 * @param a
	 *            listener
	 */
	public void removeResponseListener(ActionListener a)
	{
		if (actionListeners == null)
		{
			return;
		}
		actionListeners.removeListener(a);
		if ((actionListeners.getListenerVector() == null) || (actionListeners.getListenerVector().size() == 0))
		{
			actionListeners = null;
		}
	}

	/**
	 * Adds a listener that would be notified on the LWUIT thread of a response
	 * code that is not a 200 (OK) or 301/2 (redirect) response code.
	 * 
	 * @param a
	 *            listener
	 */
	public void addResponseCodeListener(ActionListener a)
	{
		if (responseCodeListeners == null)
		{
			responseCodeListeners = new EventDispatcher();
			responseCodeListeners.setBlocking(false);
		}
		responseCodeListeners.addListener(a);
	}

	/**
	 * Removes the given listener
	 * 
	 * @param a
	 *            listener
	 */
	public void removeResponseCodeListener(ActionListener a)
	{
		if (responseCodeListeners == null)
		{
			return;
		}
		responseCodeListeners.removeListener(a);
		if ((responseCodeListeners.getListenerVector() == null) || (responseCodeListeners.getListenerVector().size() == 0))
		{
			responseCodeListeners = null;
		}
	}

	/**
	 * Returns true if someone is listening to action response events, this is
	 * useful so we can decide whether to bother collecting data for an event in
	 * some cases since building the event object might be memory/CPU intensive.
	 * 
	 * @return true or false
	 */
	protected boolean hasResponseListeners()
	{
		return actionListeners != null;
	}

	/**
	 * Fires the response event to the listeners on this connection
	 * 
	 * @param ev
	 *            the event to fire
	 */
	protected void fireResponseListener(ActionEvent ev)
	{
		if (actionListeners != null)
		{
			actionListeners.fireActionEvent(ev);
		}
	}

	/**
	 * Indicates whether this connection request supports duplicate entries in
	 * the request queue
	 * 
	 * @return the duplicateSupported value
	 */
	public boolean isDuplicateSupported()
	{
		return duplicateSupported;
	}

	/**
	 * Indicates whether this connection request supports duplicate entries in
	 * the request queue
	 * 
	 * @param duplicateSupported
	 *            the duplicateSupported to set
	 */
	public void setDuplicateSupported(boolean duplicateSupported)
	{
		this.duplicateSupported = duplicateSupported;
	}

	/**
	 * @inheritDoc
	 */
	public int hashCode()
	{
		if (url != null)
		{
			int i = url.hashCode();
			if (requestArguments != null)
			{
				i = i ^ requestArguments.hashCode();
			}
			return i;
		}
		return 0;
	}

	/**
	 * @inheritDoc
	 */
	public boolean equals(Object o)
	{
		if ((o != null) && (o.getClass() == getClass()))
		{
			final MPConnectionRequest r = (MPConnectionRequest) o;
			// interned string comparison
			if (r.url == url)
			{
				if (requestArguments != null)
				{
					if ((r.requestArguments != null) && (requestArguments.size() == r.requestArguments.size()))
					{
						final Enumeration e = requestArguments.keys();
						while (e.hasMoreElements())
						{
							final Object key = e.nextElement();
							final Object value = requestArguments.get(key);
							final Object otherValue = r.requestArguments.get(key);
							if ((otherValue == null) || !value.equals(otherValue))
							{
								return false;
							}
						}
						return true;
					}
				}
				else
				{
					if (r.requestArguments == null)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	void validateImpl()
	{
		if (url == null)
		{
			throw new IllegalStateException("URL is null");
		}
		if (url.length() == 0)
		{
			throw new IllegalStateException("URL is empty");
		}
		validate();
	}

	/**
	 * Validates that the request has the required information before being
	 * added to the queue e.g. checks if the URL is null. This method should
	 * throw an IllegalStateException for a case where one of the values
	 * required for this connection request is missing. This method can be
	 * overridden by subclasses to add additional tests. It is usefull to do
	 * tests here since the exception will be thrown immediately when invoking
	 * addToQueue which is more intuitive to debug than the alternative.
	 */
	protected void validate()
	{
		if (!url.startsWith("http"))
		{
			throw new IllegalStateException("Only HTTP urls are supported!");
		}
	}

	/**
	 * A dialog that will be seamlessly disposed once the given request has been
	 * completed
	 * 
	 * @return the disposeOnCompletion
	 */
	public Dialog getDisposeOnCompletion()
	{
		return disposeOnCompletion;
	}

	/**
	 * A dialog that will be seamlessly disposed once the given request has been
	 * completed
	 * 
	 * @param disposeOnCompletion
	 *            the disposeOnCompletion to set
	 */
	public void setDisposeOnCompletion(Dialog disposeOnCompletion)
	{
		this.disposeOnCompletion = disposeOnCompletion;
	}

	/**
	 * This dialog will be shown when this request enters the network queue
	 * 
	 * @return the showOnInit
	 */
	public Dialog getShowOnInit()
	{
		return showOnInit;
	}

	/**
	 * This dialog will be shown when this request enters the network queue
	 * 
	 * @param showOnInit
	 *            the showOnInit to set
	 */
	public void setShowOnInit(Dialog showOnInit)
	{
		this.showOnInit = showOnInit;
	}

	/**
	 * Indicates the number of times to silently retry a connection that failed
	 * before prompting
	 * 
	 * @return the silentRetryCount
	 */
	public int getSilentRetryCount()
	{
		return silentRetryCount;
	}

	/**
	 * Indicates the number of times to silently retry a connection that failed
	 * before prompting
	 * 
	 * @param silentRetryCount
	 *            the silentRetryCount to set
	 */
	public void setSilentRetryCount(int silentRetryCount)
	{
		this.silentRetryCount = silentRetryCount;
	}
}
