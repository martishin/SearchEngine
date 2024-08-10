# SearchEngine

Implementation of a full-text on-disk search engine, consisting of two parts: 
* text index builder
* search query executor

## Running Locally
* Build the index: `gradle run --args " --idx=./index --index --dir=./"` 
* Execute queries: `gradle run --args " --idx=./index --query"`

## Testing
* Run tests: `gradle test`
  
## Usage
```
usage: [-h] --idx IDX --index [--dir DIR]
  
required arguments:  
 --idx IDX   index location  
 --index,    mode: index or query  
 --query  
   
optional arguments:  
 -h, --help  show this help message and exit  
 --dir DIR   directory for search
```
