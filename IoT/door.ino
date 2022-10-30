#include <WiFi.h>

#include <NTPClient.h>
#include <WiFiUdp.h>
#include <ArduinoJson.h>
#include <HTTPClient.h>
 
const char* ssid = process.env.wifi_id
const char* password = process.env.wifi_pw;
WiFiClient client;

//thingspeak
const char* server = "api.thingspeak.com";
String apiKey = process.env.api_key;

//날짜 시간
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP,"pool.ntp.org",32400,3600000);

//도어센서
const int doorPin = 22;
//led
const int ledPin = 23;

bool door_close_current = false; //문닫힘의 이전 상태
bool door_close_previous = true; //문닫힘의 현재 상태
void setup() {
 
  Serial.begin(115200);

  //와이파이 연결함수 불러오기
  Wifi_connect();

  //도어센서, led
  pinMode(doorPin,INPUT);
  pinMode(ledPin,OUTPUT);
  timeClient.begin();
}
 
void loop() {
  door_close_current = digitalRead(doorPin);
  
  if(door_close_current == false && door_close_previous == true){//문이 열리면
    digitalWrite(ledPin,LOW);
    door_close_previous = false; //문이 열린상태임을 기록.
    Serial.println(door_close_current); 
  }
  else if(door_close_current == true && door_close_previous == false){//문이 닫히면
    digitalWrite(ledPin,HIGH);
    door_close_previous = true;//문이 닫힌상태임을 기록.
    Serial.println(make_json());
    send_server(make_json());
    if(client.connect(server, 80)){
        thingspeak(1);
      }
    Serial.println(door_close_current);
  }

  
  delay(10);

 
}

//와이파이 연결 함수
void Wifi_connect(){
  Serial.print("Wifi Connecting to");
  Serial.println(ssid);

  WiFi.begin(ssid,password);
  Serial.println();
  Serial.print("Connecting");

  while(WiFi.status()!=WL_CONNECTED){
    delay(500);
    Serial.print(".");
  }
  Serial.println();
  Serial.println("Wifi Connected Success!");
  Serial.print("NodeMCU IP Address : ");
  Serial.println(WiFi.localIP());
}

//현재 날짜 & 시간 json으로 만들기

String make_json(){
  
  //현재 날짜
      
  timeClient.update();
  unsigned long epochTime = timeClient.getEpochTime();
  time_t rawtime = epochTime;
  struct tm *ptm = gmtime(&rawtime);
  int monthDay = ptm->tm_mday;
  int currentMonth = ptm->tm_mon+1;
  int currentYear = ptm->tm_year+1900;
  String currentDate = String(currentYear) + "-" + String(currentMonth) + "-" + String(monthDay);
  Serial.print("Current date: ");
  Serial.println(currentDate);
  
  //현재 시간 
  
  String formattedTime = timeClient.getFormattedTime();
  Serial.println(formattedTime);

  //현재 날짜 + 시간
  String CurrentDateTime = String(currentDate)+"T"+formattedTime;
  
  //json으로 만들기
  String jsondata = "";
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& root = jsonBuffer.createObject();
  root["doorClosedTime"] = CurrentDateTime;

  root.printTo(jsondata);
  return jsondata;

}

void send_server(String data){
  
  HTTPClient http;
  //POST url
  http.begin(client,"http://192.168.0.36:8080/api/dashboard/clients/door/1/");
  http.addHeader("Content-Type", "application/json");
  Serial.println(data);
  int httpResponseCode = http.PUT(data);
  Serial.print("HTTP Response code: ");
  Serial.println(httpResponseCode);

  http.end();
}

void thingspeak(int door_closed){
  String postStr = apiKey;
  postStr +="&field2=";
  postStr += String(door_closed);
  
  client.print("POST /update HTTP/1.1\n");
  client.print("Host: api.thingspeak.com\n");
  client.print("Connection: close\n");
  client.print("X-THINGSPEAKAPIKEY: " + apiKey + "\n");
  client.print("Content-Type: application/x-www-form-urlencoded\n");
  client.print("Content-Length: ");
  client.print(postStr.length());
  client.print("\n\n");
  client.print(postStr);
  
  Serial.println("Thingspeak "+String(door_closed)+"전송");

}
 