package com.neumontmc.stats_app.Controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ObjCompressor {
    /**
     * Take a obj, coverts it to bytes, then compresses those bytes.
     * @param obj Object to compress
     * @return byte array or compressed object
     * @throws IOException
     */
    public byte[] compressObject(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
        objectOutputStream.writeObject(obj);
        gzipOutputStream.finish();
        byte[] compressedObject = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        gzipOutputStream.close();
        return compressedObject;
    }

    /**
     * Take a byte array of an compressed object, then decompresses it into a Object.
     * @param bytes of an compressed object.
     * @return decompressed object.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object decompressObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
        Object returnObject = objectInputStream.readObject();
        gzipInputStream.close();
        objectInputStream.close();
        byteArrayInputStream.close();
        return returnObject;
    }
}
