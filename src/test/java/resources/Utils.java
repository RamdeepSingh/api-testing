package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;
	JsonPath js;

	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream stream = new PrintStream(new FileOutputStream("logging.txt"));

			Utils.req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(stream))
					.addFilter(ResponseLoggingFilter.logResponseTo(stream)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;
	}

	public static String getGlobalValue(String key) throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public String getJsonPath(Response response, String key) {
		
		String res = response.asString();
		this.js = new JsonPath(res);
		return js.get(key).toString();
	}

}
