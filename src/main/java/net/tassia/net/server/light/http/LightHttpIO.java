package net.tassia.net.server.light.http;

import net.tassia.io.DataInput;
import net.tassia.io.DataOutput;
import net.tassia.net.server.http.HttpMethod;
import net.tassia.net.server.http.HttpRequest;
import net.tassia.net.server.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface LightHttpIO {

    DataOutput<HttpRequest> OUTPUT_REQUEST = (out, e) -> {
        StringBuilder sb = new StringBuilder();

        // Write request-line
        sb.append(e.getMethod().name);
        sb.append(' ');
        sb.append(e.getRequestURI());
        sb.append(' ');
        sb.append(e.getProtocolVersion().name);
        sb.append("\r\n");

        // Write headers
        for (Map.Entry<String, String> entry : e.getHeaders().entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
            sb.append("\r\n");
        }

        // Write emtpy line
        sb.append("\r\n");
        out.write(sb.toString().getBytes(StandardCharsets.US_ASCII));

        // Write content
        out.write(e.getContent());
    };



    DataInput<LightHttpRequest> INPUT_REQUEST = (in) -> {
        // TODO
        return null;
    };



    DataOutput<HttpResponse> OUTPUT_RESPONSE = (out, e) -> {
        // TODO
    };

}
