// IResponse.aidl
package com.jimmy.aidlservice.aidl;

// Declare any non-default types here with import statements

interface IResponse {
    void success();
    void fail(in int code,in String msg);
}
