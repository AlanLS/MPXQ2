package io;

import java.io.UnsupportedEncodingException;
import main.Constants;
import com.sun.lwuit.events.ActionEvent;

/**
 * Event containing more meta data for network events which may be error events
 * or an update for progress indication code.
 * 
 * @author Shai Almog
 */
public class MPNetworkEvent extends ActionEvent
{
	/**
	 * Indicates that a new request is initializing its connection
	 */
	public static final int	PROGRESS_TYPE_INITIALIZING	= 1;
	/**
	 * Indicates the value for the current output stream writing
	 */
	public static final int	PROGRESS_TYPE_OUTPUT		= 2;
	/**
	 * Indicates the value for the current input stream reading
	 */
	public static final int	PROGRESS_TYPE_INPUT			= 3;
	/**
	 * Indicates that the connection request has completed
	 */
	public static final int	PROGRESS_TYPE_COMPLETED		= 4;
	private Exception		error;
	private int				progressType;
	private int				length						= -1;
	private int				received;
	private Object			metaData;
	private String			message;

	/**
	 * Constructs an event for an error message
	 * 
	 * @param source
	 *            source of the error message
	 * @param error
	 *            the exception
	 */
	public MPNetworkEvent(MPConnectionRequest source, Exception error)
	{
		super(source);
		this.error = error;
	}

	/**
	 * Constructs a response code even
	 * 
	 * @param request
	 *            the connection request indicating progress
	 * @param responseCode
	 *            the code
	 * @param message
	 *            the error message
	 */
	public MPNetworkEvent(MPConnectionRequest request, int responseCode, String message)
	{
		super(request);
		progressType = responseCode;
		this.message = message;
	}

	/**
	 * Constructs a network status update event used to update progress
	 * indicators and application logic of the current state in the
	 * NetworkManager
	 * 
	 * @param request
	 *            the connection request indicating progress
	 * @param progressType
	 */
	public MPNetworkEvent(MPConnectionRequest request, int progressType)
	{
		super(request);
		this.progressType = progressType;
	}

	/**
	 * Constructs a callback event from a connection request with some arbitrary
	 * associated meta data
	 * 
	 * @param request
	 *            the connection request
	 * @param metaData
	 *            the data associated with the event
	 */
	public MPNetworkEvent(MPConnectionRequest request, Object metaData)
	{
		super(request);
		this.metaData = metaData;
	}

	/**
	 * Equivalent to getSource() casted to connection request
	 * 
	 * @return the source connection request
	 */
	public MPConnectionRequest getConnectionRequest()
	{
		return (MPConnectionRequest) getSource();
	}

	/**
	 * @return the error
	 */
	public Exception getError()
	{
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(Exception error)
	{
		this.error = error;
	}

	/**
	 * Indicates the type of progres indication for this event
	 * 
	 * @return One of PROGRESS_TYPE_COMPLETED, PROGRESS_TYPE_INITIALIZING,
	 *         PROGRESS_TYPE_INPUT, PROGRESS_TYPE_OUTPUT
	 */
	public int getProgressType()
	{
		return progressType;
	}

	/**
	 * Indicates the response code sent by the response code listener
	 * 
	 * @return HTTP error code
	 */
	public int getResponseCode()
	{
		return progressType;
	}

	/**
	 * Returns the length of the download event or the upload event in case it
	 * is known. -1 is returned when the value is unknown.
	 * 
	 * @return the length length in bytes or -1
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * Returns the amount of data received up to this event or sent up to this
	 * event (depending on the event type).
	 * 
	 * @return the receieved or sent value
	 */
	public int getSentReceived()
	{
		return received;
	}

	/**
	 * Returns the percentage of progress for a download operation assuming
	 * length is known
	 * 
	 * @return the percentage of the download or -1
	 */
	public int getProgressPercentage()
	{
		if (length > 0)
		{
			return (int) ((((float) received) / ((float) length)) * 100.0f);
		}
		return -1;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	void setLength(int length)
	{
		this.length = length;
	}

	/**
	 * @param received
	 *            the received to set
	 */
	void setSentReceived(int received)
	{
		this.received = received;
	}

	/**
	 * @return the metaData
	 */
	public Object getMetaData()
	{
		return metaData;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	public String getStringResponse()
	{
		try
		{
			return new String(getByteResponse(), "UTF-8").trim();
		}
		catch (final UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return Constants.emptyString;
	}

	public byte[] getByteResponse()
	{
		byte[] bb = (byte[]) metaData;
		if (bb == null)
		{
			bb = new byte[0];
		}
		return (bb);
	}

	public int getActualResponseCode()
	{
		return getConnectionRequest().actualResponseCode;
	}
}
