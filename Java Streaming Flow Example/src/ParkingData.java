import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class ParkingData {

    public List<String> parkingen;

    public ParkingData(){
        parkingen = new ArrayList<>();

    }

    public void addData(JsonArray json){
        for(int i = 0; i < json.size();i++){
            parkingen.add(json.get(i).getAsJsonObject().get("parkingStatus").getAsJsonObject().get("availableCapacity")
                    +"/" + json.get(i).getAsJsonObject().get("parkingStatus").getAsJsonObject().get("totalCapacity")
                    + " - " + json.get(i).getAsJsonObject().get("name").getAsString());
        }
    }

    public String getTotalAsString(){
        int totalAvailably = 0;
        int totalCapacity = 0;

        for (String parking : parkingen) {
            totalAvailably += Integer.parseInt(parking.split(" ")[0].split("/")[0]);
            totalCapacity += Integer.parseInt(parking.split(" ")[0].split("/")[1]);
        }

        return totalAvailably + "/" + totalCapacity;
        //return totalAvailably + "";
    }
}
