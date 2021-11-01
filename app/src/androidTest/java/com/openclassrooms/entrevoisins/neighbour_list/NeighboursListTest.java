
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.R.id.list_neighbours;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class
NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT;
    private int POSITION_ITEM = 0;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getNewInstanceApiService();
        mNeighbours = mApiService.getNeighbours();
        ITEMS_COUNT = mNeighbours.size();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }



    @Test
    public void favoriteTabWithOnlyFavoriteNeighbours() {


        onView(withId(list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));

        onView(withId(R.id.favorite_float))
                .perform(click());

        Espresso.pressBack();

        onView(withId(list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM + 1, click()));

        onView(withId(R.id.favorite_float)).perform(click());

        Espresso.pressBack();

        onView(withId(R.id.container)).perform(swipeLeft());


        onView(withId(R.id.list_neighbours_favorite)).check(withItemCount(2));

    }



    @Test
    public void checkIfTheDetailIsLaunched() {

        onView(ViewMatchers.withId(list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));

        onView(withId(R.id.name_neighbourg))
                .check(matches(isDisplayed()));


    }


    @Test
    public void neighboursTextDetailDisplayedWithSuccess() {
        Neighbour neighbour = mNeighbours.get(POSITION_ITEM);

        onView(withId(list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));

        onView(ViewMatchers.withId(R.id.text_about))
                .check(matches(withText(neighbour.getAboutMe())));


    }



    @Test
    public void deleteFavoriteNeighboursWithSuccess() {

        onView(withId(list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));

        onView((withId(R.id.favorite_float)))
                .perform(click());

        Espresso.pressBack();

        onView(withId(R.id.container))
                .perform(swipeLeft());


        onView(ViewMatchers.withId(R.id.list_neighbours_favorite)).check(withItemCount(3));


        onView(ViewMatchers.withId(R.id.list_neighbours_favorite))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        onView(ViewMatchers.withId(R.id.list_neighbours_favorite)).check(withItemCount(2));
    }
}