echo off
FOR /L %%A IN (1,1,200) DO (
  java -jar D:\Java\netBeans\ProvEnabledSimpleDBClient\dist\ProvEnabledSimpleDBClient.jar pi 2000 
)