package com.example.personalLib.API.UI;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("tab")
public class TabTestView extends VerticalLayout {

    public TabTestView(){
        Tab tab1 = new Tab("Tab one");
        VerticalLayout myBooksPage = new VerticalLayout();
        //page1.setText("Page#1");

        Tab tab2 = new Tab("Tab two");
        VerticalLayout myReviewsPage = new VerticalLayout();
        //page2.setText("Page#2");
        myReviewsPage.setVisible(false);

        Tab tab3 = new Tab("Tab three");
        VerticalLayout myInfoPage = new VerticalLayout();
        //page3.setText("Page#3");
        myInfoPage.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, myBooksPage);
        tabsToPages.put(tab2, myReviewsPage);
        tabsToPages.put(tab3, myInfoPage);
        Tabs tabs = new Tabs(tab1, tab2, tab3);
        VerticalLayout pages = new VerticalLayout (myBooksPage, myReviewsPage, myInfoPage);
        Set<Component> pagesShown = Stream.of(myBooksPage)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        add(tabs, pages);
    }
}
