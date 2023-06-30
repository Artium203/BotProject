package org.example;

import javax.swing.*;

public class InfoCount extends JPanel {
    private JLabel requests = new JLabel("API requests: ");
    private JLabel uniqueUsers = new JLabel("Amount of users: ");
    private JLabel mostActiveUser = new JLabel("Most active user: ");
    private JLabel mostPopularAPI = new JLabel("Most popular api: ");
    private int numbersCount = 0;
    private int countriesCount = 0;
    private int quotesCount = 0;
    private int jokesCount = 0;
    private int catFactsCount = 0;
    JLabel picture = new JLabel();
    public int getNumbersCount() {
        return numbersCount;
    }

    public void setNumbersCount(int numbersCount) {
        this.numbersCount = numbersCount;
    }

    public int getQuotesCount() {
        return quotesCount;
    }

    public void setQuotesCount(int quotesCount) {
        this.quotesCount = quotesCount;
    }

    public int getCountriesCount() {
        return countriesCount;
    }

    public void setCountriesCount(int countriesCount) {
        this.countriesCount = countriesCount;
    }

    public int getJokesCount() {
        return jokesCount;
    }

    public void setJokesCount(int jokesCount) {
        this.jokesCount = jokesCount;
    }

    public int getCatFactsCount() {
        return catFactsCount;
    }

    public void setCatFactsCount(int catFactsCount) {
        this.catFactsCount = catFactsCount;
    }
    public JLabel getMostActiveUser() {
        return mostActiveUser;
    }
    public JLabel getUniqueUsers() {
        return uniqueUsers;
    }

    public JLabel getRequests() {
        return requests;
    }
    public JLabel getMostPopularAPI(){
        return mostPopularAPI;
    }

    public InfoCount(){
        setChartConfig();
        picture.setBounds(350, 200, 500, 300);
        this.add(picture);

        mostPopularAPI.setBounds(10,400,800,100);
        this.add(mostPopularAPI);

        mostActiveUser.setBounds(10,300,800,100);
        this.add(mostActiveUser);

        requests.setBounds(10,50,800,100);
        this.add(requests);

        uniqueUsers.setBounds(10,200,800,100);
        this.add(uniqueUsers);

        this.setLayout(null);
        this.setSize(800,600);
        this.setVisible(false);
    }
    public void setChartConfig() {
        new Thread(() -> {
            QuickChart chart = new QuickChart();
            chart.setConfig("{"
                    + "    type: 'bar',"
                    + "    data: {"
                    + "        labels: ['Numbers', 'Quotes', 'Countries', 'Jokes','Cats'],"
                    + "        datasets: [{"
                    + "            label: 'Requests',"
                    + "            data: ["+this.numbersCount+", "+this.quotesCount +","+this.countriesCount+","+this.jokesCount +","+this.catFactsCount +"]"
                    + "        }]"
                    + "    }"
                    + "}"
            );
            chart.setWidth(400);
            chart.setHeight(300);
            byte[] image= chart.toByteArray();
            ImageIcon img= new ImageIcon(image);
            this.picture.setIcon(img);
        }).start();
    }
}
