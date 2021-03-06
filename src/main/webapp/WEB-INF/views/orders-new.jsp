<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp"></jsp:include>
<div class="container bg-grayLighter">
  <div class="padding40 no-padding-bottom">
    <div class="login-form padding20 block-shadow" style="opacity: 1; transform: scale(1); transition: 0.5s;">
      <form action="/orders/new" method="post">
        <h1 class="text-light">New Order</h1>
        <hr class="thin">
        <br>
        <div class="input-control text full-size" data-role="input">
            <label for="table">Table</label>
            <input type="text" name="table" id="table" min="1" required style="padding-right: 42px;">
        </div>
        <br>
        <br>
        <div class="input-control text full-size" data-role="input">
            <label for="seat">Seat</label>
            <input type="text" name="seat" id="seat" required style="padding-right: 42px;">
        </div>
        <br>
        <br>
	
       <!--   #macro( dishesInCategory $categoryName $categoryNumber )
          <legend>$categoryName</legend>
          #foreach ($dish in $dishes)
            #if ($dish.getNumberPossibleDishes() > 0 && $dish.hasEnoughIngredients() && $dish.getCategory() == $categoryNumber)
              <label for="$dish.getName">$dish.getName()</label>
              <div class="input-control text full-size" data-role="input">
                <input type="number" name="$dish.getName()" id="dish.getId()" value="0" max="$dish.getNumberPossibleDishes()">
              </div>
              <div class="input-control text full-size" data-role="input">
                <input type="text" name="$dish.getId()comments" id="comments" placeholder="Special requests">
              </div>
              <br>
              <br>
            #end
          #end
        #end

        #if (!($dishclass.hasCategoryOf(1).isEmpty()))
          #dishesInCategory( "Appetizers", 1 )
        #end
        #if (!($dishclass.hasCategoryOf(2).isEmpty()))
          #dishesInCategory( "Entrees", 2 )
        #end

        #if (!($dishclass.hasCategoryOf(3).isEmpty()))
          #dishesInCategory( "Sides", 3 )
        #end

        #if (!($dishclass.hasCategoryOf(4).isEmpty()))
          #dishesInCategory( "Desserts", 4 )
        #end

        #if (!($dishclass.hasCategoryOf(5).isEmpty()))
          #dishesInCategory( "Drinks", 5 )
        #end
-->
        <div class="form-actions margin20 no-margin-left no-margin-right no-margin-bottom">
          <button type="submit" class="primary command-button">
            <span class="icon mif-fire"></span>
            Submit
            <small>Order and Fire</small>
          </button>
          <button type="button" class="button link"><a href="../../servers/orders/active">Back to Active Orders</a></button>
        </div>
      </form>
    </div>
  </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>