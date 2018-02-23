curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{  
   "description": "Kit mouse and mouse pad",  
   "name": "Kit Razer Mouse + Pad"  
 }' 'http://localhost:8080/api/products'

curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{  
   "description": "MOUSE RAZER",  
   "name": "Razer Mouse",  
   "parent" : { "id" : 1 }  
 }' 'http://localhost:8080/api/products'

curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{  
   "description": "MOUSE PAD RAZER",  
   "name": "Razer Mouse Pad",  
   "parent" : { "id" : 1 }  
 }' 'http://localhost:8080/api/products'

curl -X GET --header 'Accept: application/json' 'http://localhost:8080/api/products'

curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{  
   "product": {  
     "id": 2  },  
   "type": "Front View"  
 }' 'http://localhost:8080/api/images'
