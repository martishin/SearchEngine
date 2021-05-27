# SearchEngine

Implementation of full-text search engine.   
It consist of two parts: text index builder and search query executor.

Run tests:   
`gradle test`

Run indexer:  
`gradle run --args " --idx=./index --index --dir=./"`

Run query processor:   
`gradle run --args " --idx=./index --query"`
<br>
<br>
> usage: [-h] --idx IDX --index [--dir DIR]
>   
> required arguments:  
>  --idx IDX   index location  
>
>  --index,    mode: index or query  
>  --query  
>  
>  
> optional arguments:  
>  -h, --help  show this help message and exit  
>  
>  --dir DIR   directory for search  
