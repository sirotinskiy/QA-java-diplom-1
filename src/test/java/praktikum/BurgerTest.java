package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static praktikum.IngredientType.SAUCE;

public class BurgerTest {

    private float expectedPrice;

    @Spy
    private Bun bunSpy;

    @Spy
    private Ingredient ingredientSpy;

    private Burger burger;
    private String bunName = "белая";
    private float bunPrice = 22;
    private String ingredientName = "hot sauce";

    private float ingredientPrice = 100;

    @Before
    public void setUp(){
        bunSpy = new Bun(bunName, bunPrice);
        ingredientSpy = spy(new Ingredient(SAUCE, ingredientName, ingredientPrice));
        burger = new Burger();

        burger.addIngredient(ingredientSpy);
        burger.addIngredient(ingredientSpy);
        burger.addIngredient(ingredientSpy);

        burger.setBuns(bunSpy);
    }

    @Test
    public void addIngredientTest() {
        Ingredient ingredient = spy(new Ingredient(SAUCE, "www", 100));
        burger.addIngredient(ingredient);
        int expectedSize = 4;
        int actualSize = burger.ingredients.size();
        assertThat("Ingredient hasn't been added", expectedSize, equalTo(actualSize));
    }

    @Test
    public void removeIngredientTest() {
        burger.removeIngredient(0);
        int expectedSize = 2;
        int actualSize = burger.ingredients.size();
        assertThat("List should be empty", expectedSize, equalTo(actualSize));
    }

    @Test
    public void moveIngredientTest() {
        Ingredient ingredient = spy(new Ingredient(SAUCE, "www", 100));
        burger.addIngredient(ingredient);
        burger.moveIngredient(3, 2);
        Ingredient expectedIngredient = ingredient;
        Ingredient actualIngredient = burger.ingredients.get(2);
        assertThat("Incorrect order of ingredients", expectedIngredient, equalTo(actualIngredient));
    }

    @Test
    public void getPriceTest(){
        expectedPrice = bunPrice * 2 + ingredientPrice * 3;
        float actualPrice = burger.getPrice();

        assertThat(expectedPrice, equalTo(actualPrice));
    }

    @Test
    public void getReceiptTest(){
        Mockito.when(burger.getPrice()).thenReturn(bunPrice * 2 + ingredientPrice * 3);

        StringBuilder expectedReceipt = new StringBuilder();

        expectedReceipt.append(String.format("(==== %s ====)%n", bunName));

        expectedReceipt.append(String.format("= %s %s =%n", SAUCE.toString().toLowerCase(), ingredientName));
        expectedReceipt.append(String.format("= %s %s =%n", SAUCE.toString().toLowerCase(), ingredientName));
        expectedReceipt.append(String.format("= %s %s =%n", SAUCE.toString().toLowerCase(), ingredientName));

        expectedReceipt.append(String.format("(==== %s ====)%n",bunName));
        expectedReceipt.append(String.format("%nPrice: %f%n", burger.getPrice()));

        String result = expectedReceipt.toString();

        String actualReceipt = burger.getReceipt();

        assertThat(result, equalTo(actualReceipt));
    }
}