import com.google.gson.*;

import java.io.BufferedReader;
import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner lr = new Scanner(System.in);

        int opc = 0;
        int cantidad = 0;
        do{
            System.out.print("\n\n****************************************\n" +
                    "¡Bienvenido al Conversor de monedas de WnVP!\n" +
                    "1) Dolar ==> Peso argentino\n" +
                    "2) Peso argentino ==> Dolar\n" +
                    "3) Dolar ==> Real brasileño\n" +
                    "4) Real brasileño ==> Dolar\n" +
                    "5) Dolar ==> Peso colombiano\n" +
                    "6) Peso colombiano ==> DOlar\n" +
                    "0) Salir\n" +
                    "Opcion --> ");

            opc = lr.nextInt();

            System.out.print("\nIngrese la cantidad de monedas a calcular: ");
            cantidad = lr.nextInt();

            String monedaInicial;
            String monedaCambio;
            Main obj = new Main();

            switch (opc){
                case 1:
                    monedaInicial = "USD";
                    monedaCambio = "ARS";
                    obj.CalcularCambio(monedaInicial, monedaCambio, cantidad);
                    break;
                case 2:
                    monedaInicial = "ARS";
                    monedaCambio = "USD";
                    obj.CalcularCambio(monedaInicial, monedaCambio, cantidad);
                    break;
                case 3:
                    monedaInicial = "USD";
                    monedaCambio = "BRL";
                    obj.CalcularCambio(monedaInicial, monedaCambio, cantidad);
                    break;
                case 4:
                    monedaInicial = "BRL";
                    monedaCambio = "USD";
                    obj.CalcularCambio(monedaInicial, monedaCambio, cantidad);
                    break;
                case 5:
                    monedaInicial = "USD";
                    monedaCambio = "COP";
                    obj.CalcularCambio(monedaInicial, monedaCambio, cantidad);
                    break;
                case 6:
                    monedaInicial = "COP";
                    monedaCambio = "USD";
                    obj.CalcularCambio(monedaInicial, monedaCambio, cantidad);
                    break;
                case 0:
                    System.out.println("\nNos vemos pronto! =]");
                    System.exit(0);
            }
        }while(opc != 0);

    }

    public void CalcularCambio(String inicial, String cambio, int cantidad) {
        String url = "https://v6.exchangerate-api.com/v6/d49137a8be764fb7698f0097/latest/" + inicial;
        try {
            HttpURLConnection conexion = (HttpURLConnection) new URL(url).openConnection();
            conexion.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                respuesta.append(line);
            }
            reader.close();

            JsonObject respuestaJson = JsonParser.parseString(respuesta.toString()).getAsJsonObject();

            JsonObject rates = respuestaJson.getAsJsonObject("conversion_rates");
            if (rates.has(cambio)) {
                double tasaCambio = rates.get(cambio).getAsDouble();
                double total = tasaCambio * cantidad;

                System.out.println("El valor de " + cantidad + " " + inicial + " a " + cambio + " es de: " + total);
            } else {
                System.out.println("No se encontró la tasa de cambio para " + cambio);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}