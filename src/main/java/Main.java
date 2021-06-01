import com.google.gson.*;
import org.sonarsource.scanner.api.internal.shaded.okhttp.OkHttpClient;
import org.sonarsource.scanner.api.internal.shaded.okhttp.Request;
import org.sonarsource.scanner.api.internal.shaded.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {

        CoinAPI test = new CoinAPI();
        test.conn();
    }

    public static class CoinAPI {

        private final String ASSETS_URL = "https://rest.coinapi.io/v1/assets";
        private final String XCOIN_API_KEY = "C67A8EFC-2954-4095-A400-947046A81094";
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();

        void conn() throws IOException {
            requestBuilder.url(ASSETS_URL);
            requestBuilder.addHeader("X-CoinAPI-Key", XCOIN_API_KEY);
            requestBuilder.addHeader("Accept", "application/json");
            Request request = requestBuilder.build();

            Response response = client.newCall(request).execute();
            String toParse = response.body().string();
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(toParse, JsonArray.class);
            HashMap<String, Coin> temp = new HashMap<>();
            for (JsonElement o :
                    jsonArray) {
                JsonObject var = o.getAsJsonObject();
                String asset_id = String.valueOf(var.get("asset_id"));
                String name = String.valueOf(var.get("name"));
                String price1 = String.valueOf(var.get("price_usd"));
                double price;
                if (price1.equals("null")) {
                    price = 1;
                } else {
                    price = new Double(price1);
                }
                String id_icon = String.valueOf(var.get("id_icon"));
                Coin var1 = new Coin(asset_id, name, price, id_icon);
                temp.put(asset_id, var1);

            }
            System.out.println("End of bucle");

        }
    }

    public static class Coin {
        private String asset_id;
        private String name;
        private double price_usd;
        private String id_icon;

        public Coin(String asset_id, String name, double price_usd, String id_icon) {
            this.asset_id = asset_id;
            this.name = name;
            this.price_usd = price_usd;
            this.id_icon = id_icon;
        }

        public String getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(String asset_id) {
            this.asset_id = asset_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice_usd() {
            return price_usd;
        }

        public void setPrice_usd(long price_usd) {
            this.price_usd = price_usd;
        }

        public String getId_icon() {
            return id_icon;
        }

        public void setId_icon(String id_icon) {
            this.id_icon = id_icon;
        }
    }
}
