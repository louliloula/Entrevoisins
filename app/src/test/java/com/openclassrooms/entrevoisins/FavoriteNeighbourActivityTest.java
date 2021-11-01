package com.openclassrooms.entrevoisins;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class FavoriteNeighbourActivityTest extends TestCase {


    private List<Neighbour> mNeighbours;
    private NeighbourApiService mApiService;
    private Neighbour mFavoriteNeighbour;


    @Test
        public void getNeighbourFavoriteWithSuccess() {
         Neighbour neighbour = new Neighbour(3, "claire","chocolat","caulaincourt","numero","a propos");
         neighbour.setFavorite(true);
        mApiService = DI.getNeighbourApiService();
        mApiService.createNeighbour(neighbour);
        mNeighbours = mApiService.getNeighbours();
        for (Neighbour n : mNeighbours) {
            if (n.getId() == 3)
                mFavoriteNeighbour = n;

        }

        assertTrue( mFavoriteNeighbour.isFavorite());

    }


    @Test
    public void deleteNeighbourWithSuccess(){
        mApiService = DI.getNeighbourApiService();
        Neighbour neighbourToDelete = mApiService.getNeighbours().get(0);
        mApiService.deleteNeighbour(neighbourToDelete);
        assertFalse(mApiService.getNeighbours().contains(neighbourToDelete));

    }


    @Test
    public void deleteFavoriteWithSuccess(){
        Neighbour neighbour = new Neighbour(3, "claire","chocolat","caulaincourt","numero","a propos");
        neighbour.setFavorite(false);
        mApiService = DI.getNeighbourApiService();
        mApiService.createNeighbour(neighbour);
        mNeighbours = mApiService.getNeighbours();
        for (Neighbour n : mNeighbours) {
            if (n.getId() == 3)
                mFavoriteNeighbour = n;

        }

        assertFalse( mFavoriteNeighbour.isFavorite());

    }

    }




