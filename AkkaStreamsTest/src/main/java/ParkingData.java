import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class ParkingData {

    public List<String> parkingen;
    public List<String> totalParkingen;

    public ParkingData(JsonArray json) {
        parkingen = new ArrayList<>();
        totalParkingen = new ArrayList<>();
        this.addData(json);
    }

    private void addData(JsonArray json){
        for(int i = 0; i < json.size();i++){
            parkingen.add(json.get(i).getAsJsonObject().get("parkingStatus").getAsJsonObject().get("availableCapacity")
                    +"/" + json.get(i).getAsJsonObject().get("parkingStatus").getAsJsonObject().get("totalCapacity")
                    + " - " + json.get(i).getAsJsonObject().get("name").getAsString());
        }
        totalParkingen.add(getTotalOccupied());
    }

    public String getTotalOccupied(){
        int totalAvailably = 0;
        int totalCapacity = 0;

        for (String parking : parkingen) {
            totalAvailably += Integer.parseInt(parking.split(" ")[0].split("/")[0]);
            totalCapacity += Integer.parseInt(parking.split(" ")[0].split("/")[1]);
        }

        return totalAvailably + "/"+totalCapacity + " - Total";
    }
}
