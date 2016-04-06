package mobi.blindbat.aggrates;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import kz.blindbat.rateparser.EubParser;
import kz.blindbat.rateparser.Rate;
import kz.blindbat.rateparser.Rates;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.v("onCreate start");
        try {
            List<Rates> ratesList = new RetrieveRatesTask().execute().get();
            if (ratesList != null) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class RetrieveRatesTask extends AsyncTask<String, Void, List<Rates> > {

        private Exception exception;

        protected List<Rates> doInBackground(String... params) {
            LogUtil.v("doInBackground start");
            EubParser eubParser = new EubParser();
            eubParser.retrieveRates();
            LogUtil.v("Rates retrieved");
            return eubParser.getRatesList();
        }
        protected void onPostExecute(List<Rates> result) {
            TableLayout table = (TableLayout) findViewById(R.id.mainTable);
            for (Rates rates : result) {
                for (Rate rate : rates.getRates()) {
                    System.out.println("source: " + rates.getSource() +
                            "; currency: " + rate.getCurrency() +
                            "; buyRate: " + rate.getBuyRate() +
                            "; sellRate: " + rate.getSellRate() +
                            "; averageRate: " + rate.getAverageRate());
                    TableRow row = new TableRow(getApplicationContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView sourceTV = new TextView(getApplicationContext());
                    TextView currencyTV = new TextView(getApplicationContext());
                    TextView buyRateTV = new TextView(getApplicationContext());
                    TextView sellRateTV = new TextView(getApplicationContext());
                    TextView averageRateTV = new TextView(getApplicationContext());
                    row.addView(sourceTV);
                    row.addView(currencyTV);
                    row.addView(buyRateTV);
                    row.addView(sellRateTV);
                    row.addView(averageRateTV);
                    table.addView(row);
                }
            }
        }
    }
}
