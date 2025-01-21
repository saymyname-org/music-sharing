package ru.improve.openfy.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@UtilityClass
public class FileHashCalculator {

    public String getHashFromFileInputString(InputStream inputStream) throws IOException {
        byte[] fileHashByteArray = DigestUtils.sha256(inputStream);
        return Base64.getEncoder().encodeToString(fileHashByteArray);
    }
}
