package br.com.alura.tabelafipe.service;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;



public class ConsumoApi {

    HttpClient client = new HttpClient();

    @Value("${proxy.username}")
    String username;
    @Value("${proxy.pass}")
    String pass;

    public ConsumoApi() {


        }

    public String obterDados(String endereco) {


        String proxyHost = "172.19.100.75";
        int port = 8081;


        HostConfiguration config = client.getHostConfiguration();
        config.setProxy(proxyHost,port);

        Credentials credentials = new UsernamePasswordCredentials(username,pass);
        AuthScope authScope = new AuthScope(proxyHost,port);

        client.getState().setProxyCredentials(authScope,credentials);

        HttpMethod method = new GetMethod(endereco);

        method.setRequestHeader("Accept","*/*");
        method.setRequestHeader("User-Agent","PostmanRuntime/7.28.4");
        method.setRequestHeader("Connection","keep-alive");
        method.setRequestHeader("Cache-Control","no-cache");


        String response = null;

        try {
            client.executeMethod(method);

            int resposta = method.getStatusCode();
            if(method.getStatusCode() == HttpStatus.SC_OK){
                response = method.getResponseBodyAsString();
            } else{
                System.out.println(method.getResponseBodyAsString());
                throw new RuntimeException("Erro ao executar requisição HTTP");
            }

        } catch (Exception e)  {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return response;
    }
}
