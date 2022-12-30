import {CommonModule} from "@angular/common";
import {CardModule} from 'primeng/card';
import {ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';

import {NgModule} from "@angular/core";
import {ToolbarModule} from "primeng/toolbar";
import {DividerModule} from "primeng/divider";
import {TableModule} from "primeng/table";
import {InputTextModule} from "primeng/inputtext";
import {DropdownModule} from "primeng/dropdown";
import {TooltipModule} from "primeng/tooltip";
import {OverlayPanelModule} from "primeng/overlaypanel";


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CardModule,
    ButtonModule,
    MenuModule,
    ToolbarModule,
    DividerModule,
    TableModule,
    InputTextModule,
    DropdownModule,
    TooltipModule,
    OverlayPanelModule
  ],
  exports: [
    CardModule,
    ButtonModule,
    MenuModule,
    ToolbarModule,
    DividerModule,
    TableModule,
    InputTextModule,
    DropdownModule,
    TooltipModule,
    OverlayPanelModule
  ]
})
export class PrimeNgModule {
}
