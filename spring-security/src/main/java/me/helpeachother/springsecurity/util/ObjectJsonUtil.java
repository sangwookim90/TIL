package me.helpeachother.springsecurity.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

/**
 * <pre>
 * Class Name : ObjectJosinUtil.java
 * Description : 객체를 json 혹은 Json 을 객체로 변경
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 9.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 9.
*/
public class ObjectJsonUtil {

	public static String getJsonStringByObject(Object obj) throws JsonProcessingException {
		return new ObjectMapper()
			.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, false)
			.configure(MapperFeature.USE_ANNOTATIONS, false)
			.writer()
			.withDefaultPrettyPrinter()
			.writeValueAsString(obj);
	}

	public static Object getObjectByJsonString(String json, Class<?> objectClass) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
			.readValue(json, objectClass);
	}
}