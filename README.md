# Catalogue-App

**Catalogue App** shows google sheets data with taking single row as a single record. 
You can add public google sheets by specifiyng their sheets id, sheets name. 
You can specify other atributers for view display as title columns, categories and tags and so on. 

App was build with **Jetpack Compose**, using **Hilt** and **Retrofit**, **Room**. 
It has pre-populated data with information about 3 sample google sheets for demonstration:
- [**NASA**](https://docs.google.com/spreadsheets/d/1N-tIjv34uIOEosAExNkkSWCd9n--ybTVpgOxiHeGoZE/edit) - Astronomy Picture of the Day - for first 3 month of 2023 - Google Sheet has JavaScript scripts of using **NASA Api** to retrieve information.
- [**Youtube Music**](https://docs.google.com/spreadsheets/d/1ZLxdPbjzrA-lDLjVuYvvsu_zFdIzlwPxymD-qZtvOr4/edit?usp=drive_web&ouid=102505950928311293331) - Top 100 Youtube Artists - Google Sheet has JavaScript scripts of using **Youtube Api** to retrieve information about streams, mvs. 
- [**Tv-series and Shows**](https://docs.google.com/spreadsheets/d/1BSJ0-8dWzSM-TUBeNp-KYGFs5uTmJ4WPIsW3ENdvxqQ/edit) - personal collection of tv-series and shows.

## Screenshots

<p float="middle">
  <img src="https://user-images.githubusercontent.com/26432711/232549630-2e53f25b-9dd2-46bb-acc0-e8948d4e2f25.png" width="200" />
  <img src="https://user-images.githubusercontent.com/26432711/232549650-3125ce00-f23c-4857-a880-aedf5737735c.png" width="200" /> 
  <img src="https://user-images.githubusercontent.com/26432711/232549658-b7437503-84a1-4fd3-ba74-02632bed3b74.png" width="200" />
</p>

* Google Sheets is a file 
** Record is a Single Row in google sheet

## Features

- **Home** where you can see all your files* by folders with implemented search. Clicking Folder icon takes to **Folder Screen** that 
shares viewmodel with **Home Screen**. You can add new folders and delete folders except folder "Others". If in deleted folder there were files 
they will be put to folder "Others". 
Clicking Cardview(File Preview) takes to **File Screen** and action plus button takes to **Authentication File Screen (+ Add Edit File)**.

- **File Screen** display records* by categories with implemented search. 
Clicking edit button takes you to **Authentication File Screen + Add Edit File** where you can change files information like sheets id, name, 
title column and so on. Clicking Cardview(Record Preview) takes to **Record Screen** with full information. 

- **Add Edit File** contains two screens **Authentication File Screen and Add Edit File Screen**. **Authentication File Screen** that
checks existence of file and rights to the file. 
If Authentication process is succesfull clicking Next Button takes you to **Add Edit File Screen**. In that screen specifies
atributers for view display. 

## GOOGLE SHEETS API
You need google sheets Api to use this app. After obtaining Google Sheets API, include it in the local.properties file as follows:

API_KEY=insert_your_api_key_here



