import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { LandingPageComponent } from './landing-page/landing-page.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {PrimeNgModule} from "./prime-ng/prime-ng.module";
import { FlexLayoutModule } from '@angular/flex-layout';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { MonumentTableComponent } from './components/monument-table/monument-table.component';

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    ToolbarComponent,
    MonumentTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    PrimeNgModule,
    FlexLayoutModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
