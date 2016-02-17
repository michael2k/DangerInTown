/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/kms/android/studio/DangerInTown/app/src/main/aidl/org/safedu/danger/location/ITrackRecordingService.aidl
 */
package org.safedu.danger.location;
/**
 * MyTracks service.
 * This service is the process that actually records and manages tracks.
 */
public interface ITrackRecordingService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.safedu.danger.location.ITrackRecordingService
{
private static final java.lang.String DESCRIPTOR = "org.safedu.danger.location.ITrackRecordingService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.safedu.danger.location.ITrackRecordingService interface,
 * generating a proxy if needed.
 */
public static org.safedu.danger.location.ITrackRecordingService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.safedu.danger.location.ITrackRecordingService))) {
return ((org.safedu.danger.location.ITrackRecordingService)iin);
}
return new org.safedu.danger.location.ITrackRecordingService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_startTracking:
{
data.enforceInterface(DESCRIPTOR);
this.startTracking();
reply.writeNoException();
return true;
}
case TRANSACTION_stopTracking:
{
data.enforceInterface(DESCRIPTOR);
this.stopTracking();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.safedu.danger.location.ITrackRecordingService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
   * Starts tracking.
   */
@Override public void startTracking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startTracking, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
   * Stops tracking.
   */
@Override public void stopTracking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopTracking, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_startTracking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopTracking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
   * Starts tracking.
   */
public void startTracking() throws android.os.RemoteException;
/**
   * Stops tracking.
   */
public void stopTracking() throws android.os.RemoteException;
}
