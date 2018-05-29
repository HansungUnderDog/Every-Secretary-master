package cse.underdog.org.underdog_client.memo;

public class MemoInfo {
    int memo_id;
    String context;

    public String getContext(){
        return context;
    }

    public MemoInfo(){

    }

    public MemoInfo(String context){
        this.context=context;
    }

    public MemoInfo(String context, int memo_id){
        this.context = context;
        this.memo_id = memo_id;
    }

}
