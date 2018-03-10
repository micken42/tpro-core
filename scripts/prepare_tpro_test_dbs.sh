#!/bin/bash
set -e

echo " - - - - P R E P A R E - T E S T - U S E R - M A N A G E M E N T - D B - - - - "
mysql -utpro -ptpro -e "create database if not exists tpro_test_user_management"
mysql -utpro -ptpro tpro_test_user_management < sql/drop_user_management.sql
mysql -utpro -ptpro tpro_test_user_management < sql/create_user_management.sql

echo " - - - - P R E P A R E - T E S T - P L U G I N - B O O K S T O R E - D B - - - - "
mysql -utpro -ptpro -e "create database if not exists tpro_test_plugin_bookstore"