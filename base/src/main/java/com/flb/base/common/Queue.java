package com.flb.base.common;

public class Queue<T>
{
	protected final static int MIN_SHRINK_SIZE = 1024;

	protected T[] items;
	boolean bWait = false;
	protected int iCount = 0;
	protected int iStart = 0, iEnd = 0;
	protected int iSuggestedSize, iSize = 0;

	public Queue()
	{
		this(4);
	}

	public Queue(int suggestedSize)
	{
		iSize = iSuggestedSize = suggestedSize;
		items = newArray(iSize);
	}

	public synchronized void clear()
	{
		iCount = iStart = iEnd = 0;
		iSize = iSuggestedSize;
		items = newArray(iSize);
	}

	public synchronized boolean hasElements()
	{
		return (iCount != 0);
	}

	public synchronized int size()
	{
		return iCount;
	}

	public synchronized void prepend(T item)
	{
		if (iCount == iSize)
		{
			makeMoreRoom();
		}

		if (iStart == 0)
		{
			iStart = iSize - 1;
		}
		else
		{
			iStart--;
		}

		items[iStart] = item;
		iCount++;

		if (iCount == 1)
		{
			notify();
		}
	}

	public synchronized void append(T item)
	{
		append0(item, iCount == 0 || bWait);
	}

	public synchronized void appendSilent(T item)
	{
		append0(item, false);
	}

	public synchronized void appendLoud(T item)
	{
		append0(item, true);
	}

	protected void append0(T item, boolean notify)
	{
		if (iCount == iSize)
		{
			makeMoreRoom();
		}

		items[iEnd] = item;
		iEnd = (iEnd + 1) % iSize;
		iCount++;

		if (notify)
		{
			notify();
			bWait = false;
		}
	}

	public synchronized T getNonBlocking()
	{
		if (iCount == 0)
		{
			return null;
		}

		T retval = items[iStart];
		items[iStart] = null;

		iStart = (iStart + 1) % iSize;
		iCount--;

		return retval;
	}

	public synchronized void waitForItem() 
	{
		while (iCount == 0)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	public synchronized T get(long maxwait)
	{
		if (iCount == 0)
		{
			try
			{
				wait(maxwait);
			}
			catch (InterruptedException e)
			{
			}

			if (iCount == 0)
			{
				return null;
			}
		}

		return get();
	}

	public synchronized T get()
	{
		while (iCount == 0)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
			}
		}

		T retval = items[iStart];
		items[iStart] = null;

		iStart = (iStart + 1) % iSize;
		iCount--;

		if ((iSize > MIN_SHRINK_SIZE) && (iSize > iSuggestedSize) && (iCount < (iSize >> 3)))
		{
			shrink();
		}

		return retval;
	}

	private void makeMoreRoom()
	{
		T[] tempitems = newArray(iSize * 2);
		System.arraycopy(items, iStart, tempitems, 0, iSize - iStart);
		System.arraycopy(items, 0, tempitems, iSize - iStart, iEnd);

		iStart = 0;
		iEnd = iSize;
		iSize *= 2;
		items = tempitems;
	}

	private void shrink()
	{
		T[] tempitems = newArray(iSize / 2);

		if (iStart > iEnd)
		{
			System.arraycopy(items, iStart, tempitems, 0, iSize - iStart);
			System.arraycopy(items, 0, tempitems, iSize - iStart, iEnd + 1);
		}
		else
		{
			System.arraycopy(items, iStart, tempitems, 0, iEnd - iStart + 1);
		}

		iSize = iSize / 2;
		iStart = 0;
		iEnd = iCount;
		items = tempitems;
	}

	@SuppressWarnings("unchecked")
	private T[] newArray(int size)
	{
		return (T[]) new Object[size];
	}

	public synchronized void await()
	{
		try
		{
			bWait = true;

			wait();
		}
		catch (InterruptedException e)
		{
		}
	}

	public synchronized void await(long maxwait)
	{
		try
		{
			wait(maxwait);
		}
		catch (InterruptedException e)
		{
		}
	}

	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("[count=").append(iCount);
		buf.append(", size=").append(iSize);
		buf.append(", start=").append(iStart);
		buf.append(", end=").append(iEnd);
		buf.append(", elements={");

		for (int i = 0; i < iCount; i++)
		{
			int pos = (i + iStart) % iSize;

			if (i > 0)
			{
				buf.append(", ");
			}

			buf.append(items[pos]);
		}

		return buf.append("}]").toString();
	}
}