var stompClient = null;

function setConnected(connected) {
    if (connected) {
        $("#connectionState").css('background-color', 'green');
    } else {
        $("#connectionState").css('background-color', 'red');
    }
}

function connect() {
    //if already connected
    if (stompClient != null) {
        if (stompClient.connected) {
            return;
        }
    }
    var socket = new SockJS('http://localhost:8080/live_view');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        var register_channel = stompClient.subscribe('/user/topic/poker_register', function (currentState) {
            showState(JSON.parse(currentState.body));
            register_channel.unsubscribe();
            stompClient.subscribe('/topic/poker_updates', function (update) {
                showState(JSON.parse(update.body));
            });

        });
        stompClient.send("/app/register", {}, {});

        console.log('Connected: ' + frame);
    });
}

function showState(state) {
    $("#globalValues")[0].innerHTML = globalValuesHtml(state);

    $("#players").empty();
    state.players.forEach(function(player) {
        $("#players").append(playerHtml(player));
    });
    console.log("State updated.");
}

function globalValuesHtml(state) {
    return '' +
    '<div class="panel panel-default">' +
        '<div class="panel panel-heading"><h3>Global Variables</h3></div>' +
        '<div class="panel panel-body">' +
            '<div>Pot: ' + state.pot + '</div>' +
            '<div>CurrentPlayer: ' + state.currentPlayer.name + '</div>' +
            '<div>Dealer: ' + state.dealer.name + '</div>' +
        '</div>' +
    '</div>';
}

function playerHtml(player) {
    return '' +
        '<div class="col-xs-6">' +
            '<div class="panel panel-default">' +
                '<div class="panel panel-heading">' +
                    '<h3 class="panel-heading">' + player.name + '</h3>' +
                '</div>' +
                '<div class="panel panel-body">' +
                    '<div>Cards: ' + player.cards + '</div>' +
                    '<div>Money: ' + player.money + '</div>' +
                    '<div>Bet: ' + player.bet + '</div>' +
                '</div>' +
            '</div>' +
        '</div>';
}

function MockState() {
    var p1 = new Player("JOHN", 400, 50, "KK");
    var p2 = new Player("SANDRA", 600, 50, "A2");
    var p3 = new Player("TOM", 100, 100,  "72");

    this.players = [p1, p2, p3];
    this.name = "JOHN";
    this.pot = 2000;
    this.currentPlayer = p1;
    this.dealer = p2
}

function Player(name, money, bet, cards) {
    this.name = name;
    this.money = money;
    this.bet = bet;
    this.cards = cards;
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

$(function () {
    $("#connectionState").css('background-color', 'red');
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
});
