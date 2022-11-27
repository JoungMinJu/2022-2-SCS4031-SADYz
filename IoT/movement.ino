#include <NTPClient.h>
#include <WiFiUdp.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>
#include <ESP8266WiFi.h>

const char* ssid = process.env.wifi_id
const char* password = process.env.wifi_pw;

//초음파 센서 
const int trigPin = 12; //D5
const int echoPin = 14; //D6
const int ledPin = 16; // D0
//PIR 센서
const int pir = 13; //D7
WiFiClient client;

//thinkspeak
const char* server = "api.thingspeak.com";
String apiKey = process.env.api_key;

//날짜 시간
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP,"pool.ntp.org",32400,3600000);


void setup() {
  Serial.begin(115200);
  Serial.println();
  //와이파이 연결
  Wifi_connect();

  //초음파센서
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input

  //pir센서
  pinMode(pir,INPUT);

  //led
  pinMode(ledPin, OUTPUT);
  //시간
  timeClient.begin();
}

// the loop function runs over and over again forever
void loop() {
  check_movement();
  delay(10);
  
}

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
  Serial.print("NodeMCU 게이트웨이 : ");
  Serial.println(WiFi.gatewayIP().toString());
  Serial.print("NodeMCU 서브넷 : ");
  Serial.println(WiFi.subnetMask().toString());
}

void check_movement(){
  //초음파센서
  long duration, distance;
  digitalWrite(trigPin, LOW);  
  delayMicroseconds(3); 
  
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(12); 
  
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;


  //pir센서
  int state = digitalRead(pir);

  //움직임 감지되면
  //초음파 센서 값을 기준(거리 몇 cm 이하면 사람)으로 계산하여
  //참일 경우, json으로 현재 날짜 저장

  if (state == 1){
    if (distance<30){
      digitalWrite(ledPin,HIGH);
      Serial.println("Activiated");
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
      root["lastMovedTime"] = CurrentDateTime;
      root["location"] = "kitchen" ; //or "bathroom"
      root.printTo(jsondata);

      //서버로 움직임 시간 전송
      if(client.connect(server, 80)){
        send_server(jsondata);
      }

      Serial.println("Waiting..."); 
      delay(10);
      digitalWrite(ledPin,LOW);
    }
      
  }
  else{
    digitalWrite(ledPin,LOW);
  }
  
}


void send_server(String data){
  
  HTTPClient http;
  //POST url
  http.begin(client,"http://172.30.17.229:8080/api/dashboard/clients/time/xxx-xxxx-xxxx");

  http.addHeader("Content-Type", "application/json");
  Serial.println(data);
  int httpResponseCode = http.POST(data);

  if(httpResponseCode == 500){
     http.addHeader("Content-Type", "application/json");
     httpResponseCode = http.POST(data);
    
  }
  Serial.print("HTTP Response code: ");
  Serial.println(httpResponseCode);

  http.end();
}