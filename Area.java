package board;
public enum Area {
    NORMAL(0),RIVER(1),TRAP(2),DEN(4);
    public int type;
    Area(int type){
        this.type = type;// 0.Normal 1.river 2.Trap 3.Den
    }
}
