function getLatestData(tab) {
  let endpoint = '/frontend-test';
  
  if (tab === 'FIN') {
    endpoint += '/finance';
  } else if (tab === 'TECH') {
    endpoint += '/technology';
  } else if (tab === 'PROP') {
    endpoint += '/properties';
  }

  // Update the URL using history.pushState
  history.pushState(null, null, endpoint);

  $.ajax({
      url: `/latest-data/${tab}`,
      type: 'GET',
      dataType: 'json',
      success: function(StockDataDto) {
        $('#stock-content').empty();
        console.log('Response Data: ', StockDataDto)
        StockDataDto.forEach(stock => {
          const formattedMarketTime = formatDateTime(stock.regularMarketTime);
          const formattedMarketPrice = formatPrice(stock.regularMarketPrice);
          const formattedPctPriceChange = formatPctPriceChange(stock.regularMarketPricePercentageChange);
          const formattedAbsPriceChange = formatAbsPriceChange(stock.regularMarketPriceAbsoluteChange);
          const priceChangeClass = stock.regularMarketPricePercentageChange > 0 ? 'up' : (stock.regularMarketPricePercentageChange < 0 ? 'down' : 'neutral');

          const row =         
            `<tr>
              <td class = "symbol_col">${stock.symbol}</td>
              <td class = "price_col">${formattedMarketPrice}</td>
              <td class = "price_change_col ${priceChangeClass}">
                <span class="price_pctchange">
                  <span>${formattedPctPriceChange}</span>
                  <span>%</span>
                </span>
                <span class="price_abschange">
                  <span>(</span>
                  <span>${formattedAbsPriceChange}</span>
                  <span>)</span>
                </span>
              </td>
              <td class = "markettime_col">${formattedMarketTime}</td>
            </tr>`;
          $('#stock-content').append(row);
        });
      },
      error: function(xhr, status, error) {
          console.error('Error fetching data: ', error);
      }
  });

}

function formatDateTime(dateTime) {
  const [datePart, timePart] = dateTime.split('T');
  const [year, month, day] = datePart.split('-');
  const [hours, minutes, seconds] = timePart.split(':');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function formatPrice(price) {
  // Check if the input is a valid number
  if (isNaN(price)) {
      return "Invalid Price";
  }

  // Round the price to two decimal places
  const formattedPrice = Number(price).toFixed(2);

  return formattedPrice;
}

function formatAbsPriceChange(regularMarketPriceAbsoluteChange){
    // Check if the input is a valid number
  if (isNaN(regularMarketPriceAbsoluteChange)) {
      return "Invalid Price Change";
  }
  // Round the price to two decimal places
  const formattedAbsPriceChange = (regularMarketPriceAbsoluteChange > 0 ? '+' : '') + Number(regularMarketPriceAbsoluteChange).toFixed(2);

  return formattedAbsPriceChange;
}

function formatPctPriceChange(regularMarketPricePercentageChange){
  if (isNaN(regularMarketPricePercentageChange)) {
    return "Invalid Price Change";
  }
  // Round the price to two decimal places
  const formattedAbsPriceChange = (regularMarketPricePercentageChange > 0 ? '▲' : (regularMarketPricePercentageChange < 0 ? '▼' : '-')) + Number(regularMarketPricePercentageChange).toFixed(2);

  return formattedAbsPriceChange;
}

$(document).ready(function () {
  setInterval(function () {
    $.ajax({
      url: "/latest-data",
      type: "GET",
      success: function (data) {
        data.forEach(function (stock, index) {
          // Find the corresponding row using index or another unique identifier
          var row = $(`data-stock-id=${stock.symbol}`);

          if (row.length) {
            // Update last updated timestamp
            row
              .find(".markettime_col")
              .text(formatDateTime(stock.regularMarketTime)); 

            // Update current price
            row
              .find(".price_col")
              .text("$" + stock.regularMarketPrice.toFixed(2));

            var priceClass =
            stock.regularMarketPricePercentageChange > 0
                ? "up"
                : stock.regularMarketPricePercentageChange < 0
                ? "down"
                : "neutral";

            var priceChangeElement = row.find(".price_change_col");

            priceChangeElement
              .removeClass("up down neutral")
              .addClass(priceClass);
            
            row
              .find(".price_abschange")
              .text(formatPctPriceChange(stock.regularMarketPriceAbsoluteChange))

            row
              .find(".price_pctchange")
              .text(formatPctPriceChange(stock.regularMarketPricePercentageChange))
          }
        });
      },
      error: function (err) {
        console.error("Error fetching updated stock data:", err);
      },
    });
  }, 30000); // Update every 30 seconds
});