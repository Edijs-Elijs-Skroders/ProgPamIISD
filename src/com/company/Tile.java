package com.company;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    final int tileNr; //final- can be set only once

    Tile(int tileNr){
        this.tileNr = tileNr;
    }

    public abstract boolean isEmpty();

    public abstract Piece getPiece();


    //Creates a method and a map for all possible(64) TileEmpty objects
    private static Map<Integer, TileEmpty> createTilesEmpty(){

        final Map<Integer, TileEmpty> tileEmptyMap =  new HashMap<>();

        for(int a = 0; a < 64; a++){
            tileEmptyMap.put(a, new TileEmpty(a));
        }
        return tileEmptyMap;
    }

    private static final Map<Integer, TileEmpty> tileEmptyMap = createTilesEmpty();


    //method that creates tiles and returns either TileEmpty or TileFull objects
    //Uses the tilesEmptyMap to return the value for tileNr key

    public static Tile createTile(final int tileNr, final Piece piece){
        if(piece == null){
            return tileEmptyMap.get(tileNr);
        }
        else {
            return new TileFull(tileNr,piece);
        }
    }

}

