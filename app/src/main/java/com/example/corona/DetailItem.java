package com.example.corona;

public class DetailItem {
    private String tv_provinsid;
    private String tv_positifd;
    private String tv_sembuhd;
    private String tv_meninggald;


public DetailItem(String pro, String pos, String sem, String men){
    this.tv_provinsid = pro;
    this.tv_positifd = pos;
    this.tv_sembuhd = sem;
    this.tv_meninggald = men;
}

public String getTv_provinsid(){
    return tv_provinsid;
}
public String getTv_positifd(){
    return tv_positifd;
}
public String getTv_sembuhd(){
    return tv_sembuhd;
}
public String getTv_meninggald(){
    return tv_meninggald;
}

}