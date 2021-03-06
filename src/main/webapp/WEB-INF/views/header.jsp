<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Punjab Dumdar</title>

    <link rel="stylesheet" href="https://cdn.rawgit.com/olton/Metro-UI-CSS/master/build/css/metro.min.css">
    <link rel="stylesheet" href="https://cdn.rawgit.com/olton/Metro-UI-CSS/master/build/css/metro-responsive.min.css">
    <link rel="stylesheet" href="https://cdn.rawgit.com/olton/Metro-UI-CSS/master/build/css/metro-schemes.min.css">
    <link rel="stylesheet" href="https://cdn.rawgit.com/olton/Metro-UI-CSS/master/build/css/metro-rtl.min.css">
    <link rel="stylesheet" href="https://cdn.rawgit.com/olton/Metro-UI-CSS/master/build/css/metro-icons.min.css">

    <link rel="stylesheet" href="/css/styles.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/olton/Metro-UI-CSS/master/build/js/metro.min.js">
    </script>
  </head>
  <body class="bg-steel Site">
    <div class="app-bar bg-dark">
        <a class="app-bar-element" href="/" class="metro-title">Punjab Dumdar</a>
        <span class="app-bar-divider"></span>
        <ul class="app-bar-menu">
          <li><a href="kitchen/orders/active">Kitchen</a></li>
          <li><a href="servers/orders/active">Servers</a></li>
          <li>
              <a href="" class="dropdown-toggle">Manager</a>
              <ul class="d-menu" data-role="dropdown">
                <li><a href="manager/inventory">Current Inventory</a></li>
                <li><a href="manager/delivery">New Delivery</a></li>
                <li><a href="manager/dishes">Menu Items</a></li>
                <li><a href="manager/new-dish">New Dish</a></li>
                <li><a href="manager/new-ingredient">New Ingredient</a></li>
              </ul>
          </li>
        </ul>
    </div>
    <div class="container page-content Site-content">

