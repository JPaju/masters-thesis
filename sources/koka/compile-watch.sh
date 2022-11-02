#!/bin/bash

if [[ $# -eq 0 ]] ; then
    echo 'ERROR: Provide path to Koka file'
    exit 0
fi

echo "Filename: $1";
fswatch $1 --event-flags --event Updated --one-per-batch --latency 1.5 | xargs -n 1 -I {} koka $1
