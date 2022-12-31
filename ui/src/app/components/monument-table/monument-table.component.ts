import {Component, OnInit} from '@angular/core';
import {Monument} from "../../domain/monument";
import {MonumentService} from "../../core/monument.service";

@Component({
  selector: 'app-monument-table',
  templateUrl: './monument-table.component.html',
  styleUrls: ['./monument-table.component.scss']
})
export class MonumentTableComponent implements OnInit {

  monuments: Monument[] = [];
  totalRecords: number = 0;
  loading: boolean = false;

  constructor(private api: MonumentService) {
  }

  ngOnInit(): void {
    this.loading = true;
  }

  public loadMonuments($event: any) {
    this.loading = true;
    const page = $event.first / $event.rows + 1;
    const sort = this.resolveSort($event.sortField, $event.sortOrder);
    const filters = this.buildFilters($event.filters);
    this.api.getMonuments(page, $event.rows, sort, filters).subscribe((data: any) => {
      this.monuments = data.content;
      this.totalRecords = data.totalElements;
      this.loading = false;
    });
  }

  public showPicture(photoUrl: string) {
    Object.assign(document.createElement('a'), {
      target: '_blank',
      rel: 'photo-url',
      href: photoUrl
    }).click();
  }

  private resolveSort = (field: string, order: number): string => {
    if (field == null) {
      return '';
    }
    if (order == -1) {
      return field + ',DESC';
    } else {
      return field + ',ASC';
    }
  };

  // not working ? - &
  private buildFilters = (filters: any): string => {
    let filter = '';
    if (filters.name?.value != null) {
      filter = filter + this.append(filter, 'name', filters.name.value);
    }
    if (filters.county?.value != null) {
      filter = filter + this.append(filter, 'county', filters.county.value);
    }
    if (filters.description?.value != null) {
      filter = filter + this.append(filter, 'description', filters.description.value);
    }
    if (filters.objectNumber?.value != null) {
      filter = filter + this.append(filter, 'objectNumber', filters.objectNumber.value);
    }
    if (filters.type?.value != null) {
      filter = filter + this.append(filter, 'type', filters.type.value);
    }
    if (filters.community?.value != null) {
      filter = filter + this.append(filter, 'community', filters.community.value);
    }
    if (filters.address?.value != null) {
      filter = filter + this.append(filter, 'address', filters.address.value);
    }
    if (filters.justifications?.value != null) {
      filter = filter + this.append(filter, 'justifications', filters.justifications.value);
    }
    if (filters.scopeOfprotections?.value != null) {
      filter = filter + this.append(filter, 'scopeOfProtections', filters.scopeOfprotections.value);
    }
    return filter;
  }

  private append = (filter: string, constant: string, value: any): string => {
    if (constant == undefined) return filter;
    const appendix = filter === '' ? '?' : '&';
    return appendix + constant + '=' + value;
  }
}
